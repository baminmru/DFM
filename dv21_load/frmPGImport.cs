using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Windows.Forms;
using System.Data;
using System.Reflection;
using dv21_util;
using System.Security.AccessControl;
using System.Collections.Generic;


namespace dv21
{

    internal partial class frmPGImport : Form
    {
        private StringBuilder sOut;
        private string Path;
        private int iListIndex;
        private bool bDontClear;
        private pgDataSource DS;
        private string Log;


        private string n;
        private int cnt;
        private object i;
        private bool inClick;







        //private void execBlock(ref LATIRGenerator.BlockHolder b, ref string modulename)
        //{
        //    string s;
        //    string[] lines;
        //    int i;
        //    lines = Strings.Split(b.BlockCode, Constants.vbCrLf, Compare: CompareMethod.Text);
        //    s = "";
        //    pb.Minimum = Information.LBound(lines);
        //    pb.Maximum = Information.UBound(lines);
        //    pb.Value = Information.LBound(lines);
        //    pb.Visible = true;
        //    var loopTo = Information.UBound(lines);
        //    for (i = Information.LBound(lines); i <= loopTo; i++)
        //    {
        //        pb.Value = i;
        //        lblmsg.Text = i.ToString() + " (" + Information.UBound(lines).ToString() + ")";
        //        Application.DoEvents();
        //        if (Strings.UCase(Strings.Trim(lines[i])) == "GO")
        //        {

        //            if (!string.IsNullOrEmpty(Strings.Trim(s)))
        //            {
        //                try
        //                {
        //                    if (!chkNoExec.Checked)
        //                    {
        //                        DS.Execute(s);
        //                        if (!string.IsNullOrEmpty(DS.LastMessage()))
        //                        {
        //                            lblmsg.Text = DS.LastMessage();
        //                            Application.DoEvents();
        //                        }
        //                    }

        //                    Debug.Print(s);
        //                    sOut.AppendLine(s);
        //                    sOut.AppendLine("$$");
        //                }

        //                catch (Exception ex)
        //                {
        //                    txtLog.Text = txtLog.Text + Constants.vbCrLf + b.BlockName + ":" + modulename + Constants.vbCrLf + s + Constants.vbCrLf + "------------------------" + Constants.vbCrLf + ex.Message;
        //                    Debug.Print(s);
        //                    Debug.Print(ex.Message);
        //                    s = "";
        //                }
        //                Application.DoEvents();

        //            }
        //            s = "";
        //        }

        //        else
        //        {
        //            s = s + Constants.vbCrLf + lines[i];
        //        }

        //    }
        //    pb.Visible = false;


        //}


         private void ImportSingleSchema( string schema)
        {
            DataTable dt = new DataTable();
            dt = DS.ReadData("SELECT t.table_name, obj_description(pgc.oid, 'pg_class') comment FROM information_schema.tables t INNER JOIN pg_catalog.pg_class pgc ON t.table_name = pgc.relname WHERE t.table_type = 'BASE TABLE' AND t.table_schema = '" + schema + "'; ");
            CardDefinition cd = new CardDefinition();
            cd.Alias = schema;
            cd.Schema = schema;
            cd.ID = Guid.NewGuid().ToString();
            cd.Sections = new SectionType[dt.Rows.Count];
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                cd.Sections[i] = new SectionType();
                cd.Sections[i].ID = Guid.NewGuid().ToString();
                cd.Sections[i].Type = SectionTypeType.coll;
                cd.Sections[i].Sequnce = i;
                cd.Sections[i].Alias = dt.Rows[i]["table_name"].ToString();
                cd.Sections[i].Name = new LocalizedStringsLocalizedString[1];
                cd.Sections[i].Name[0] = new LocalizedStringsLocalizedString();
                cd.Sections[i].Name[0].Value = MyUtils.CropComment(dt.Rows[i]["comment"].ToString());
                cd.Sections[i].Documentation = dt.Rows[i]["comment"].ToString();
                cd.Sections[i].Name[0].Language = "ru";

                DataTable dtFld = new DataTable();
                dtFld = DS.ReadData(
                    @"select
                            c.table_schema,
                            c.table_name,
                            c.column_name,
                            c.is_nullable,
                            c.data_type, 
                            c.udt_name,
                            c.character_maximum_length len,
                            pgd.description
                        from pg_catalog.pg_statio_all_tables as st
                        inner join pg_catalog.pg_description pgd on (
                            pgd.objoid = st.relid
                        )
                        inner join information_schema.columns c on (
                            pgd.objsubid   = c.ordinal_position and
                            c.table_schema = st.schemaname and
                            c.table_name   = st.relname
                        ) where c.table_name = '" + cd.Sections[i].Alias + "' and c.table_schema ='" + schema + "'" +
                    @"union all                        
                        select
                            c.table_schema,
                            c.table_name,
                            c.column_name,
                            c.is_nullable,
                            c.data_type, 
                            c.udt_name,
                            c.character_maximum_length len,
                            pgd.description
                        from pg_catalog.pg_partitioned_table  as st
                        inner join pg_catalog.pg_description pgd on (
                            pgd.objoid = st.partrelid
                        )
                        inner join pg_class pc on pc.oid = st.partrelid
                        join pg_namespace sp on sp.oid  = pc.relnamespace  
                        inner join information_schema.columns c on(
                            pgd.objsubid = c.ordinal_position and
                            c.table_schema = sp.nspname and
                            c.table_name = pc.relname
                        ) where c.table_name = '" + cd.Sections[i].Alias + "' and c.table_schema = '" + schema + "'; "
                    );


                DataTable dtPK = new DataTable();
                dtPK = DS.ReadData(@" select tc.constraint_type,kcu.constraint_schema ,kcu.constraint_name ,  kcu.table_schema ,kcu.table_name ,kcu.column_name,kcu.ordinal_position pos   from information_schema.key_column_usage kcu
JOIN information_schema.table_constraints tc ON tc.constraint_schema = kcu.constraint_schema 
     AND tc.constraint_name = kcu.constraint_name
     and tc.constraint_type = 'PRIMARY KEY'
 where kcu.table_name ='" + cd.Sections[i].Alias + "' and kcu.constraint_catalog ='" + DS.DataBaseName + "' and kcu.constraint_schema ='" + schema + "' ");


                cd.Sections[i].Field = new FieldType[dtFld.Rows.Count];

                for (int j = 0; j < dtFld.Rows.Count; j++)
                {

                    cd.Sections[i].Field[j] = new FieldType();
                    cd.Sections[i].Field[j].ID = Guid.NewGuid().ToString();

                    if (dtFld.Rows[j]["is_nullable"].ToString() == "YES")
                    {
                        cd.Sections[i].Field[j].NotNull = false;
                        cd.Sections[i].Field[j].NotNullSpecified = true;
                    }
                    else
                    {
                        cd.Sections[i].Field[j].NotNull = true;
                        cd.Sections[i].Field[j].NotNullSpecified = true;
                    }

                    cd.Sections[i].Field[j].Type = (FieldTypeType)MapBaseType(dtFld.Rows[j]["udt_name"].ToString());

                    cd.Sections[i].Field[j].Documentation = "Import data type: " + dtFld.Rows[j]["udt_name"].ToString() ;

                    if (dtFld.Rows[j]["data_type"].ToString() == "USER-DEFINED")
                        if (dtFld.Rows[j]["udt_name"].ToString().ToLower().Contains("enum"))
                        {
                            cd.Sections[i].Field[j].Type = FieldTypeType.@enum;
                            cd.Sections[i].Field[j].EnumName = dtFld.Rows[j]["udt_name"].ToString();
                        }
                
            

                    cd.Sections[i].Field[j].Alias = dtFld.Rows[j]["column_name"].ToString();

                    if (dtPK.Rows.Count > 0)
                    {

                        if (dtPK.Rows[0]["column_name"].ToString() == dtFld.Rows[j]["column_name"].ToString())
                        {
                            cd.Sections[i].Field[j].UseforPK = true;
                            cd.Sections[i].Field[j].UseforPKSpecified = true;
                           
                            if (cd.Sections[i].Field[j].Type != FieldTypeType.@int  || cd.Sections[i].Field[j].Type != FieldTypeType.int8)
                            {
                                cd.Sections[i].Field[j].IsBrief = true;
                                cd.Sections[i].Field[j].IsBriefSpecified = true;
                            }
                            else
                            {
                                cd.Sections[i].IdType = cd.Sections[i].Field[j].Type.ToString();
                            }
                        }
                    }


                    if (dtFld.Rows[j]["len"].ToString() != "")
                    {
                        cd.Sections[i].Field[j].Max = (int)dtFld.Rows[j]["len"];
                        cd.Sections[i].Field[j].MaxSpecified = true;
                        cd.Sections[i].Field[j].Documentation += " (" + dtFld.Rows[j]["len"].ToString() + ")";
                    }

                    cd.Sections[i].Field[j].Name = new LocalizedStringsLocalizedString[1];
                    cd.Sections[i].Field[j].Name[0] = new LocalizedStringsLocalizedString();
                    cd.Sections[i].Field[j].Name[0].Value = MyUtils.CropComment(dtFld.Rows[j]["description"].ToString());
                    cd.Sections[i].Field[j].Documentation += "\r\n" +dtFld.Rows[j]["description"].ToString();
                    cd.Sections[i].Field[j].Name[0].Language = "ru";

                }


            }


            for (int i = 0; i < cd.Sections.Length; i++)
            {
                DataTable dtFK = new DataTable();
                dtFK = DS.ReadData(@"select kcu.constraint_schema ,kcu.constraint_name ,  kcu.table_schema ,kcu.table_name ,kcu.column_name , kcu.ordinal_position pos ,
                            ccu.table_schema to_schema , ccu.table_name to_table, ccu.column_name to_column  from information_schema.key_column_usage kcu
                            join
                             information_schema.constraint_column_usage ccu on  kcu.constraint_name  = ccu.constraint_name
  
                            JOIN information_schema.table_constraints tc ON tc.constraint_schema = kcu.constraint_schema 
                                 AND tc.constraint_name = kcu.constraint_name
                                 and tc.constraint_type = 'FOREIGN KEY'
                             where kcu.table_name ='" + cd.Sections[i].Alias + "' and kcu.constraint_catalog ='" + DS.DataBaseName + "' and kcu.constraint_schema ='" + schema + "' and  ccu.table_schema ='" + schema + "' and ccu.table_catalog ='" + DS.DataBaseName + "'"
                 );

                for (int r = 0; r < dtFK.Rows.Count; r++)
                {

                    for (int j = 0; j < cd.Sections[i].Field.Length; j++)
                    {
                        if (cd.Sections[i].Field[j].Alias == dtFK.Rows[r]["column_name"].ToString())
                        {
                            cd.Sections[i].Field[j].Reference = true;
                            cd.Sections[i].Field[j].ReferenceSpecified = true;
                            cd.Sections[i].Field[j].RefType = cd.ID.ToString();

                            for (int k = 0; k < cd.Sections.Length; k++)
                            {
                                if (cd.Sections[k].Alias == dtFK.Rows[r]["to_table"].ToString())
                                {
                                    cd.Sections[i].Field[j].RefSection = cd.Sections[k].ID.ToString();
                                }
                            }

                        }
                    }


                }


            }

            MyUtils.SerializeObject(txtSaveTo.Text, cd);
        }

        private List<dv21.CardDefinition> cards;
        private dv21.DefFile prj;

        private void ImportSchemas()
        {
            string ImportFolder;
            //string ProjectFileName;
            FileInfo f = new FileInfo(txtSaveTo.Text);
            ImportFolder = f.DirectoryName;
            cards = new List<CardDefinition>();
            prj = new dv21.DefFile();
            prj.Paths = new List<String>();


            // save import project
            for (int i = 0; i < lstBlocks.CheckedItems.Count; i++)
            {
                prj.Paths.Add(  ImportFolder + @"\" + lstBlocks.CheckedItems[i].ToString() + ".xml");
            }
            MyUtils.SerializeObject(txtSaveTo.Text, prj);

            for (int c = 0; c < lstBlocks.CheckedItems.Count; c++)
            {
                string schema = lstBlocks.CheckedItems[c].ToString();
                DataTable dt = new DataTable();
                dt = DS.ReadData("SELECT t.table_name, obj_description(pgc.oid, 'pg_class') comment FROM information_schema.tables t INNER JOIN pg_catalog.pg_class pgc ON t.table_name = pgc.relname WHERE t.table_type = 'BASE TABLE' AND t.table_schema = '" + schema + "'; ");

                CardDefinition cd = new CardDefinition();
                cd.Alias = schema;
                cd.Schema = schema;
                cd.ID = Guid.NewGuid().ToString();
                cd.Sections = new SectionType[dt.Rows.Count];
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    cd.Sections[i] = new SectionType();
                    cd.Sections[i].ID = Guid.NewGuid().ToString();
                    cd.Sections[i].Type = SectionTypeType.coll;
                    cd.Sections[i].Sequnce = i;
                    cd.Sections[i].Alias = dt.Rows[i]["table_name"].ToString();
                    cd.Sections[i].Name = new LocalizedStringsLocalizedString[1];
                    cd.Sections[i].Name[0] = new LocalizedStringsLocalizedString();
                    cd.Sections[i].Name[0].Value = MyUtils.CropComment(dt.Rows[i]["comment"].ToString());
                    cd.Sections[i].Documentation = dt.Rows[i]["comment"].ToString();
                    cd.Sections[i].Name[0].Language = "ru";

                    DataTable dtFld = new DataTable();
                    dtFld = DS.ReadData(
                        @"select
                            c.table_schema,
                            c.table_name,
                            c.column_name,
                            c.is_nullable,
                            c.data_type, 
                            c.udt_name,
                            c.character_maximum_length len,
                            pgd.description
                        from pg_catalog.pg_statio_all_tables as st
                        inner join pg_catalog.pg_description pgd on (
                            pgd.objoid = st.relid
                        )
                        inner join information_schema.columns c on (
                            pgd.objsubid   = c.ordinal_position and
                            c.table_schema = st.schemaname and
                            c.table_name   = st.relname
                        ) where c.table_name = '" + cd.Sections[i].Alias + "' and c.table_schema ='" + schema + "'" +
                        @"union all                        
                        select
                            c.table_schema,
                            c.table_name,
                            c.column_name,
                            c.is_nullable,
                            c.data_type, 
                            c.udt_name,
                            c.character_maximum_length len,
                            pgd.description
                        from pg_catalog.pg_partitioned_table  as st
                        inner join pg_catalog.pg_description pgd on (
                            pgd.objoid = st.partrelid
                        )
                        inner join pg_class pc on pc.oid = st.partrelid
                        join pg_namespace sp on sp.oid  = pc.relnamespace  
                        inner join information_schema.columns c on(
                            pgd.objsubid = c.ordinal_position and
                            c.table_schema = sp.nspname and
                            c.table_name = pc.relname
                        ) where c.table_name = '" + cd.Sections[i].Alias + "' and c.table_schema = '" + schema + "'; "                      
                        );


                    DataTable dtPK = new DataTable();
                    dtPK = DS.ReadData(@" select tc.constraint_type,kcu.constraint_schema ,kcu.constraint_name ,  kcu.table_schema ,kcu.table_name ,kcu.column_name,kcu.ordinal_position pos   from information_schema.key_column_usage kcu
    JOIN information_schema.table_constraints tc ON tc.constraint_schema = kcu.constraint_schema 
         AND tc.constraint_name = kcu.constraint_name
         and tc.constraint_type = 'PRIMARY KEY'
     where kcu.table_name ='" + cd.Sections[i].Alias + "' and kcu.constraint_catalog ='" + DS.DataBaseName + "' and kcu.constraint_schema ='" + schema + "' ");


                    cd.Sections[i].Field = new FieldType[dtFld.Rows.Count];

                    for (int j = 0; j < dtFld.Rows.Count; j++)
                    {

                        cd.Sections[i].Field[j] = new FieldType();
                        cd.Sections[i].Field[j].ID = Guid.NewGuid().ToString();
                        if (dtFld.Rows[j]["is_nullable"].ToString() == "YES")
                        {
                            cd.Sections[i].Field[j].NotNull = false;
                            cd.Sections[i].Field[j].NotNullSpecified = true;
                        }
                        else
                        {
                            cd.Sections[i].Field[j].NotNull = true;
                            cd.Sections[i].Field[j].NotNullSpecified = true;
                        }

                        cd.Sections[i].Field[j].Type = (FieldTypeType)MapBaseType(dtFld.Rows[j]["udt_name"].ToString());

                        cd.Sections[i].Field[j].Documentation = "Import data type: " + dtFld.Rows[j]["udt_name"].ToString();

                        if (dtFld.Rows[j]["data_type"].ToString() == "USER-DEFINED")
                            if (dtFld.Rows[j]["udt_name"].ToString().ToLower().Contains("enum"))
                            {
                                cd.Sections[i].Field[j].Type = FieldTypeType.@enum;
                                cd.Sections[i].Field[j].EnumName = dtFld.Rows[j]["udt_name"].ToString();
                            }




                        cd.Sections[i].Field[j].Alias = dtFld.Rows[j]["column_name"].ToString();

                        if (dtPK.Rows.Count > 0)
                        {

                            if (dtPK.Rows[0]["column_name"].ToString() == dtFld.Rows[j]["column_name"].ToString())
                            {
                                cd.Sections[i].Field[j].UseforPK = true;
                                cd.Sections[i].Field[j].UseforPKSpecified = true;
                                if (cd.Sections[i].Field[j].Type != FieldTypeType.@int || cd.Sections[i].Field[j].Type != FieldTypeType.int8)
                                {
                                    cd.Sections[i].Field[j].IsBrief = true;
                                    cd.Sections[i].Field[j].IsBriefSpecified = true;
                                }
                                else
                                {
                                    cd.Sections[i].IdType = cd.Sections[i].Field[j].Type.ToString();
                                }

                            }
                        }


                        if (dtFld.Rows[j]["len"].ToString() != "")
                        {
                            cd.Sections[i].Field[j].Max = (int)dtFld.Rows[j]["len"];
                            cd.Sections[i].Field[j].MaxSpecified = true;
                            cd.Sections[i].Field[j].Documentation += " (" + dtFld.Rows[j]["len"].ToString() + ")";
                        }

                        cd.Sections[i].Field[j].Name = new LocalizedStringsLocalizedString[1];
                        cd.Sections[i].Field[j].Name[0] = new LocalizedStringsLocalizedString();
                        cd.Sections[i].Field[j].Name[0].Value = MyUtils.CropComment( dtFld.Rows[j]["description"].ToString());
                        cd.Sections[i].Field[j].Documentation += "\r\n" + dtFld.Rows[j]["description"].ToString();
                        cd.Sections[i].Field[j].Name[0].Language = "ru";

                    }


                }
                MyUtils.SerializeObject(ImportFolder + @"\" + schema + ".xml", cd);

            }

            // load import project
            MyUtils.LoadCards(cards, txtSaveTo.Text);
            for (int c = 0; c < lstBlocks.CheckedItems.Count; c++)
            {
                string schema = lstBlocks.CheckedItems[c].ToString();

                CardDefinition cd = MyUtils.GetReferencedTypeByName(cards, schema);

                for (int i = 0; i < cd.Sections.Length; i++)
                {
                    DataTable dtFK = new DataTable();
                    dtFK = DS.ReadData(@"select kcu.constraint_schema ,kcu.constraint_name ,  kcu.table_schema ,kcu.table_name ,kcu.column_name , kcu.ordinal_position pos ,
                            ccu.table_schema to_schema , ccu.table_name to_table, ccu.column_name to_column  from information_schema.key_column_usage kcu
                            join
                             information_schema.constraint_column_usage ccu on  kcu.constraint_name  = ccu.constraint_name
  
                            JOIN information_schema.table_constraints tc ON tc.constraint_schema = kcu.constraint_schema 
                                 AND tc.constraint_name = kcu.constraint_name
                                 and tc.constraint_type = 'FOREIGN KEY'
                             where kcu.table_name ='" + cd.Sections[i].Alias + "' and kcu.constraint_catalog ='" + DS.DataBaseName + "' and kcu.constraint_schema ='" + schema + "'  and ccu.table_catalog ='" + DS.DataBaseName + "'"
                     );

                    for (int r = 0; r < dtFK.Rows.Count; r++)
                    {
                        if (cd.Sections[i] != null && cd.Sections[i].Field != null) {

                            for (int j = 0; j < cd.Sections[i].Field.Length; j++)
                            {
                                if (cd.Sections[i].Field[j].Alias == dtFK.Rows[r]["column_name"].ToString())
                                {


                                    CardDefinition refType = null;
                                    SectionType refSection = null;
                                    string refSchema = dtFK.Rows[r]["to_schema"].ToString();

                                    refType = MyUtils.GetReferencedTypeByName(cards, dtFK.Rows[r]["to_schema"].ToString());
                                    if (refType != null)
                                        refSection = MyUtils.GetReferencedSectionByName(refType.Sections, dtFK.Rows[r]["to_table"].ToString());

                                    if (refSection != null)
                                    {
                                        cd.Sections[i].Field[j].Reference = true;
                                        cd.Sections[i].Field[j].ReferenceSpecified = true;
                                        cd.Sections[i].Field[j].RefType = refType.ID.ToString();
                                        cd.Sections[i].Field[j].RefSection = refSection.ID.ToString();
                                    }


                                }
                            }
                        }


                    }


                }
                MyUtils.SerializeObject(ImportFolder + @"\" + schema + ".xml", cd);


            }







        }



        private void cmdGo_Click(object eventSender, EventArgs eventArgs)
        {

            if (txtSaveTo.Text != "")
            {
                

                if (lstBlocks.CheckedItems.Count == 1)
                {
                    string schema = lstBlocks.CheckedItems[0].ToString();
                    ImportSingleSchema(schema);
                    MessageBox.Show("Импорт завершен");

                }

                if (lstBlocks.CheckedItems.Count > 1)
                {
                    ImportSchemas();
                    MessageBox.Show("Импорт завершен");
                }


            }

        }


        private int MapBaseType(string pgType)
        {



            FieldTypeType v = FieldTypeType.@int;
            switch (pgType.ToLower())
            {
                case "int4":
                    v= FieldTypeType.@int;
                    break;

                case "int8":
                    v = FieldTypeType.int8;
                    break;

                case "integer":
                    v = FieldTypeType.@int;
                    break;

                case "bigint":
                    v = FieldTypeType.int8;
                    break;

                case "bool":
                    v = FieldTypeType.@bool;
                    break;

                case "date":
                    v = FieldTypeType.datetime;
                    break;


                case "timestamp":
                    v = FieldTypeType.timestamp;
                    break;

                case "timestamptz":
                    v = FieldTypeType.timestamptz;
                    break;


                case "char":
                    v = FieldTypeType.@string;
                    break;

                case "bpchar":
                    v = FieldTypeType.@string;
                    break;

                case "character":
                    v = FieldTypeType.@string;
                    break;


                case "enum":
                    v = FieldTypeType.@enum;
                    break;

                case "bytea":
                    v = FieldTypeType.bitmask;
                    break;

                case "uuid":
                    v = FieldTypeType.uniqueid;
                    break;

                case "text":
                    v = FieldTypeType.text;
                    break;


                case "varchar":
                    v = FieldTypeType.@string;
                    break;

                case "character varying":
                    v = FieldTypeType.@string;
                    break;


                case "jsonb":
                    v = FieldTypeType.json;
                    break;

                case "json":
                    v = FieldTypeType.json;
                    break;

                case "xml":
                    v = FieldTypeType.json;
                    break;

                case "numeric":
                    v = FieldTypeType.@double;
                    break;

            }
            return (int) v;

        }



        



        private void Form1_Load(object sender, EventArgs e)
        {
            txtServer.Text =  "localhost";
            

        }

        private void lblmsg_Click(object sender, EventArgs e)
        {

        }

     

        private void cmdConnect_Click(object sender, EventArgs e)
        {

            try
            {

            
            DS = new pgDataSource();
            DS.Server = txtServer.Text;
            DS.DataBaseName = txtDatabase.Text;
            DS.UserName = txtLogin.Text;
            DS.Password = txtPassword.Text;
            DS.Port = txtPort.Text;
            DS.Integrated = false;
            if (!DS.ServerLogIn())
            {
                MessageBox.Show("Не удается подключиться к PgSQL");
                
                DS = null;
                return;
            }

            DataTable shemas = DS.ReadData("SELECT schema_name FROM information_schema.schemata s  where catalog_name = '"+ DS.DataBaseName + "'");

            for (int i = 0; i < shemas.Rows.Count; i++) {
                lstBlocks.Items.Add(shemas.Rows[i]["schema_name"].ToString());
            }
            } catch (System.Exception ex)
            {
                MessageBox.Show(ex.Message);
            }


        }

        private void lstBlocks_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void cmdSaveTo_Click(object sender, EventArgs e)
        {
            if(dlgSaveTo.ShowDialog() == DialogResult.OK)
            {
                txtSaveTo.Text = dlgSaveTo.FileName;
            }
        }
    }
}