
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Schema;

namespace mapper
{
    public class PGGen
    {

        private StringBuilder declare;
        private StringBuilder sb;
        private StringBuilder fk;
        private StringBuilder enums;
        private StringBuilder result;
        public string API { get; set; }
        public pgDataSource ds { get; set; }
       
      

        public PGGen()
        {
           
            
            fk = new StringBuilder();
            enums = new StringBuilder();
            result = new StringBuilder();
            sb = new StringBuilder();
            declare = new StringBuilder();

        }


        private string MapBaseType(string dsType)
        {
            switch (dsType.ToUpper())
            {
                case "DSOPERDAY":
                    return "text"; // "date";

                case "DSINT_KEY":
                    return "text"; //"int8";

                case "DSTINYINT":
                    return "text"; //"int8";

                case "DSDATETIME":
                    return "text"; //"timestamptz";

                case "DSNUMBER20":
                    return "text";

                case "DSBRIEFNAME":
                    return "text";

                case "DSMONEY":
                    return "text"; //"numeric(18,8)";

                case "DSIDENTIFIER":
                    return "text";


                case "DSVARFULLNAME40":
                    return "text";

                case "DSFULLNAME":
                    return "text";


                case "DSCOMMENT":
                    return "text";

                case "DSSPID":
                    return "text"; //"int4";

                    /*

                                    case "bool":
                                        return "boolean";

                                    case "datetime":
                                        return "date";



                                    case "enum":
                                        return "integer";







                                    case "text":
                                        return "text";


                                    case "fileid":
                                        return "bytea";

                                    case "image":
                                        return "bytea";

                                    case "json":
                                        return "jsonb";

                                    case "float":
                                        return "numeric(18,8)";

                      */


            }
            return "text";
        
	}

        private void  MakeSectionType(String s)
        {
            if (s == "") return;
            

            DataTable tbl = ds.ReadData("select distinct field_name, comment, field_type, field_order, table_comment from src_data where api ='" + API + "' and table_name = '" + s + "' order by field_order");

            string t = s.ToLower();



            StringBuilder cc = new StringBuilder();

            sb.AppendLine(" -- start " + t);

            cc.AppendLine("COMMENT ON TABLE " + CurrentSchema  + t + " IS '" + tbl.Rows[0]["table_comment"].ToString() + "';");

            sb.AppendLine("CREATE TABLE IF NOT EXISTS  " + CurrentSchema  + t + "(");

            sb.AppendLine("\t\tspid  int4");

            int i;
            if (tbl != null) {
                for (i = 0; i < tbl.Rows.Count; i++)
                {
                    string fo = tbl.Rows[i]["field_name"].ToString();
                    string f = fo.ToLower();
                    string c = tbl.Rows[i]["comment"].ToString();
                    c.Replace("'", " ");

                    string pgtype = MapBaseType(tbl.Rows[i]["field_type"].ToString());
                        cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + t + "." + f+ " IS '" + c + "';");


                    //////////////////////////////////

                 

                    string func = MyUtils.MapFunc(fo);

                    if (func == "?" || func == "")
                    {
                        string caser = MyUtils.MakeCase(f, c);
                        if (caser != "")
                        {
                            sb.AppendLine("\t\t," + f + " " + pgtype + "");
                            sb.AppendLine("\t\t," + f + "_text text ");
                        }
                        else
                        {
                            sb.AppendLine("\t\t," + f + " " + pgtype + "");
                        }

                    }
                    else
                    {
                        sb.AppendLine("\t\t , " + f + " " + pgtype + "");
                        sb.AppendLine("\t\t," + f + "_text text ");

                    }

                    /////////////////////////////////

                    //sb.AppendLine("\t\t," + f + " " + pgtype + "");
                }

            }
                

          


            sb.AppendLine(");"); // end of create table

            sb.AppendLine(" -- comments for " + t);
            sb.AppendLine(cc.ToString());
            sb.AppendLine(" -- end " + t);

            
            
            sb.AppendLine("");




        }

        private string CurrentSchema;

        public string Generate()
        {
            CurrentSchema = "migration";


            result.AppendLine("CREATE SCHEMA IF NOT EXISTS " + CurrentSchema + ";");
            CurrentSchema = CurrentSchema + ".";


            GenerateOne();

            result.AppendLine(enums.ToString());
            result.AppendLine(sb.ToString());
            result.AppendLine(fk.ToString());

            return   result.ToString();
        }


        public void GenerateOne()
        {

            int i;


            sb.AppendLine("-- start " + API + ";");

            //DataTable tbl = ds.ReadData("select distinct table_name from src_data where api ='" + API + "'");

            DataTable tbl = ds.ReadData("select distinct table_name from src_data where  api ='" + API + "' order by table_name");


            for (i = 0; i < tbl.Rows.Count ; i++)
            {
                MakeSectionType(tbl.Rows[i]["table_name"].ToString());
            }

            sb.AppendLine("-- end " + API + ";");
            sb.AppendLine("");

        }


        public string GenerateAll()
        {

            CurrentSchema = "migration";


            result.AppendLine("CREATE SCHEMA IF NOT EXISTS " + CurrentSchema + ";");
            CurrentSchema = CurrentSchema + ".";

            //DataTable a = ds.ReadData("select distinct api from src_data where api like 'API%'");
            DataTable a = ds.ReadData("select distinct api from used_api order by api");

            int i;
            for (i = 0; i < a.Rows.Count; i++)
            {
                API = a.Rows[i]["api"].ToString();
                GenerateOne();
            }


            

            result.AppendLine(enums.ToString());
            result.AppendLine(sb.ToString());
            result.AppendLine(fk.ToString());

            return result.ToString();

        }



        public string BuildMap()
        {
            DataTable m = ds.ReadData("select distinct map_name  from map_data ");
            for (int i = 0; i < m.Rows.Count; i++)
            {
                BuildMapScript(m.Rows[i]["map_name"].ToString());

            }
            return result.ToString();
        }


        private string GetKeyField( string SrcAPI, string SrcTBL)
        {
           DataTable keys = ds.ReadData("select field_name from  src_data   where entity_key = true and api='" + SrcAPI + "' and table_name = '" + SrcTBL + "'");
            if (keys.Rows.Count > 0)
            {
                return keys.Rows[0]["field_name"].ToString();
            }
            return "";

        }


            public void BuildMapScript( string mapping)
        {


            DataTable md = ds.ReadData("select *  from map_data where map_name ='" + mapping + "' order by to_table, to_field");
            StringBuilder fields = new StringBuilder();
            StringBuilder values = new StringBuilder();

            sb = new StringBuilder();
            declare = new StringBuilder();


            result.AppendLine("-- " +mapping );
            result.AppendLine("DO $$");

            string tbl = md.Rows[0]["to_table"].ToString() ;
            string schema = md.Rows[0]["to_schema"].ToString();
            if (schema == "") schema = "public";

            declare.AppendLine("DECLARE migration_id  text ; ");
            declare.AppendLine("DECLARE migration_spid  text ; ");
            declare.AppendLine("DECLARE migration_list cursor for  select id from  test ;  --  change to real select");


            for (int i = 0; i < md.Rows.Count; i++)
            {

                if (tbl != md.Rows[i]["to_table"].ToString())
                {
                    sb.AppendLine("");


                    sb.AppendLine("\t\tinsert into " + schema +"."+ tbl.ToLower() + "(\r\n\t\t\t" + fields.ToString() + "\r\n\t\t\t)values (\r\n\t\t\t " + values.ToString() + "\r\n\t\t\t);");
                    sb.AppendLine("");
                    tbl = md.Rows[i]["to_table"].ToString();
                    values.Clear();
                    fields.Clear();
                }



                string n = "v_" + md.Rows[i]["to_table"].ToString() + "_" + md.Rows[i]["to_field"].ToString();
                n = n.ToLower();
                declare.AppendLine("DECLARE " + n + "  text ; ");


                if (md.Rows[i]["comment"].ToString() != "")
                {
                    sb.AppendLine("\t\t\t-- " + md.Rows[i]["comment"].ToString());
                }

                sb.AppendLine("\t\tselect  migration." + md.Rows[i]["table_name"].ToString().ToLower() + "." + md.Rows[i]["field_name"].ToString().ToLower() + "  into " + n);
                sb.AppendLine("\t\t\tfrom  migration." + md.Rows[i]["table_name"].ToString().ToLower() );
                string fkey = GetKeyField(md.Rows[i]["api"].ToString(), md.Rows[i]["table_name"].ToString());

                if (fkey != "")
                {
                    if (md.Rows[i]["condition"].ToString().Trim() != "")
                        sb.AppendLine("\t\t\twhere spid = migration_spid and " +fkey.ToLower() + " = migration_id  and " + md.Rows[i]["condition"].ToString() + "  LIMIT 1;");
                    else
                        sb.AppendLine("\t\t\twhere spid = migration_spid and " + fkey.ToLower() + " = migration_id   LIMIT 1;");
                    sb.AppendLine("");
                }
                else
                {

                    if (md.Rows[i]["condition"].ToString().Trim() != "")
                        sb.AppendLine("\t\t\twhere spid = migration_spid and  " + md.Rows[i]["condition"].ToString() + "  LIMIT 1;");
                    else
                        sb.AppendLine("\t\t\twhere spid = migration_spid  LIMIT 1;");
                    sb.AppendLine("");

                }

                if (fields.ToString() != "")
                    fields.Append(",\r\n\t\t\t");
                fields.Append(md.Rows[i]["to_field"].ToString().ToLower());

                if (values.ToString() != "")
                    values.Append(",\r\n\t\t\t");
                values.Append(n);

            }

            if (fields.ToString() != "")
            {
                sb.AppendLine("");
                sb.AppendLine("\t\tinsert into " + tbl.ToLower() + "(\r\n\t\t\t" + fields.ToString() + "\r\n\t\t\t)values (\r\n\t\t\t " + values.ToString() + "\r\n\t\t\t);");
                sb.AppendLine("");
            }



            result.AppendLine(declare.ToString());
            result.AppendLine("BEGIN");
            result.AppendLine("OPEN migration_list;");
            result.AppendLine("LOOP");
            result.AppendLine("FETCH NEXT FROM migration_list INTO migration_id;");
            result.AppendLine("EXIT WHEN NOT FOUND;");
            result.AppendLine(sb.ToString());
            result.AppendLine("END LOOP;");
            result.AppendLine("CLOSE migration_list;");
            result.AppendLine("END $$;");
            result.AppendLine("LANGUAGE PLPGSQL;");
            result.AppendLine("-- end of " + mapping);
            result.AppendLine("");
        }



    }




}
