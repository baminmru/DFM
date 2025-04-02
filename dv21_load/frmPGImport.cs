using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Windows.Forms;
using System.Data;
using System.Reflection;
using dv21_util;


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



        private void cmdGo_Click(object eventSender, EventArgs eventArgs)
        {

            if (lstBlocks.CheckedItems.Count > 0)
            {
                string schema = lstBlocks.CheckedItems[0].ToString();
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
                    cd.Sections[i].Alias = dt.Rows[i]["table_name"].ToString();
                    cd.Sections[i].Name = new LocalizedStringsLocalizedString[1];
                    cd.Sections[i].Name[0] = new LocalizedStringsLocalizedString();
                    cd.Sections[i].Name[0].Value = dt.Rows[i]["comment"].ToString();
                    cd.Sections[i].Name[0].Language = "ru";

                    DataTable dtFld = new DataTable();
                    dtFld = DS.ReadData(@"select
                        c.table_schema,
                        c.table_name,
                        c.column_name,
                        c.is_nullable,
                        c.data_type, 
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
                    ) where c.table_name = '" + cd.Sections[i].Alias + "' and c.table_schema ='" + schema + "';");

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

                        cd.Sections[i].Field[j].Type = (FieldTypeType)MapBaseType(dtFld.Rows[j]["data_type"].ToString());


                        cd.Sections[i].Field[j].Alias = dtFld.Rows[j]["column_name"].ToString();

                        if (dtFld.Rows[j]["len"].ToString() != "")
                        {
                            cd.Sections[i].Field[j].Max = (int)dtFld.Rows[j]["len"];
                            cd.Sections[i].Field[j].MaxSpecified = true;
                        }

                        cd.Sections[i].Field[j].Name = new LocalizedStringsLocalizedString[1];
                        cd.Sections[i].Field[j].Name[0] = new LocalizedStringsLocalizedString();
                        cd.Sections[i].Field[j].Name[0].Value = dtFld.Rows[j]["description"].ToString();
                        cd.Sections[i].Field[j].Name[0].Language = "ru";

                    }


                }
                MyUtils.SerializeObject(@"c:\bami\dfm\generated\" + schema + ".xml", cd);

            }

        }


        private int MapBaseType(string dv21Type)
        {
            //switch (dv21Type)
            //{
            //    case "int":
            //        return "integer";

            //    case "bool":
            //        return "boolean";

            //    case "datetime":
            //        return "date";

            //    case "enum":
            //        return "integer";

            //    case "bitmask":
            //        return "bytea";

            //    case "uniqueid":
            //        return "UUID";

            //    case "userid":
            //        return "varchar (64)";

            //    case "string":
            //        return "varchar";

            //    case "text":
            //        return "text";

            //    case "unistring":
            //        return "varchar";

            //    case "fileid":
            //        return "bytea";

            //    case "image":
            //        return "bytea";

            //    case "json":
            //        return "jsonb";

            //    case "float":
            //        return "numeric(18,8)";

            //    case "double":
            //        return "numeric(18,8)";


            //}
            return 1;

        }



        //            if (string.IsNullOrEmpty(txtData.Text))
        //                return;
        //            sOut = new StringBuilder();
        //            sOut.AppendLine("DELIMITER $$");
        //            txtLog.Text = "";
        //            lstBlocks.Items.Clear();

        //            string srv;
        //            srv = Interaction.GetSetting("PGSQLDBInstall", "setup", "servers", "");
        //            if (!srv.Contains(txtServer.Text))
        //            {
        //                if (!string.IsNullOrEmpty(srv))
        //                {
        //                    srv = srv + ";";
        //                }
        //                srv = srv + txtServer.Text;
        //                Interaction.SaveSetting("PGSQLDBInstall", "setup", "servers", srv);
        //            }

        //            if (!chkNoExec.Checked)
        //            {
        //                DS = new pgDataSource();
        //                DS.Server = txtServer.Text;


        //                DS.DataBaseName = txtDatabase.Text;
        //                DS.UserName = txtLogin.Text;
        //                DS.Password = txtPassword.Text;
        //                DS.Integrated = false;
        //                if (!DS.ServerLogIn())
        //                {
        //                    Interaction.MsgBox("Не удается подключиться к PgSQL", MsgBoxStyle.Critical);
        //                    // UPGRADE_NOTE: Object DS may not be destroyed until it is garbage collected. Click for more: 'ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?keyword="6E35BFF6-CD74-4B09-9689-3E1A43DF8969"'
        //                    DS = null;
        //                    return;
        //                }
        //            }


        //            GenResp = new LATIRGenerator.Response();
        //            GenPrj = GenResp.Project;
        //            GenPrj.Load(txtData.Text);
        //            ;

        //#error Cannot convert OnErrorResumeNextStatementSyntax - see comment for details
        //            /* Cannot convert OnErrorResumeNextStatementSyntax, CONVERSION ERROR: Conversion for OnErrorResumeNextStatement not implemented, please report this issue in 'On Error Resume Next' at character 5679


        //                        Input:

        //                                On Error Resume Next

        //                         */
        //            int i, j;
        //            var loopTo = GenPrj.Modules.Count;
        //            for (i = 1; i <= loopTo; i++)
        //            {
        //                var loopTo1 = GenPrj.Modules.Item(i).Blocks.Count;
        //                for (j = 1; j <= loopTo1; j++)
        //                    lstBlocks.Items.Add(GenPrj.Modules.Item(i).ModuleName + ":" + GenPrj.Modules.Item(i).Blocks.Item(j).BlockName);
        //            }
        //            int k;
        //            k = 0;
        //            var loopTo2 = GenPrj.Modules.Count;
        //            for (i = 1; i <= loopTo2; i++)
        //            {
        //                var loopTo3 = GenPrj.Modules.Item(i).Blocks.Count;
        //                for (j = 1; j <= loopTo3; j++)
        //                {
        //                    // If lstBlocks.Selected(k) = True Then
        //                    var argb = GenPrj.Modules.Item(i).Blocks.Item(j);
        //                    var argmodulename = GenPrj.Modules.Item(i).ModuleName;
        //                    execBlock(ref argb, ref argmodulename);
        //                    lstBlocks.SetItemChecked(k, true);
        //                    k = k + 1;
        //                }
        //            }


        //            var outfile = new StreamWriter(txtData.Text + "_out.txt");

        //            outfile.Write(sOut.ToString());

        //            outfile.Close();

        //            if (chkNoExec.Checked)
        //            {

        //                Interaction.MsgBox("Формирование скрипта завершено. Файл:" + Constants.vbCrLf + txtData.Text + "_out.txt", MsgBoxStyle.Information);
        //            }


        //            else if (string.IsNullOrEmpty(txtLog.Text))
        //            {
        //                Interaction.MsgBox("Создание базы данных завершено", MsgBoxStyle.Information);
        //            }
        //            else
        //            {
        //                Interaction.MsgBox("Создание базы данных завершено с ошибками", MsgBoxStyle.Critical);
        //            }
        //            DS = null;

        //            GenResp = (object)null;

        //            GenPrj = (object)null;
 






        private void Form1_Load(object sender, EventArgs e)
        {
            txtServer.Text =  "localhost";
            

        }

        private void lblmsg_Click(object sender, EventArgs e)
        {

        }

     

        private void cmdConnect_Click(object sender, EventArgs e)
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


        }

        private void lstBlocks_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}