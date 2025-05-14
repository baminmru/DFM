
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

        private StringBuilder sb;
        private StringBuilder fk;
        private StringBuilder enums;
        private StringBuilder result;
        public string API { get; set; }
        public pgDataSource ds { get; set; }
       
      

        public PGGen()
        {
           
            sb = new StringBuilder();
            fk = new StringBuilder();
            enums = new StringBuilder();
            result = new StringBuilder();


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
            

            DataTable tbl = ds.ReadData("select * from src_data where api ='" + API + "' and table_name = '" + s + "' order by field_order");

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

                    string f = tbl.Rows[i]["field_name"].ToString().ToLower();
                    string c = tbl.Rows[i]["comment"].ToString();

                    string pgtype = MapBaseType(tbl.Rows[i]["field_type"].ToString());
                        cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + t + "." + f+ " IS '" + c + "';");
                    sb.AppendLine("\t\t," + f + " " + pgtype + "");
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

            DataTable tbl = ds.ReadData("select distinct table_name from src_data where api ='" + API + "'");

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

            DataTable a = ds.ReadData("select distinct api from src_data where api like 'API%'");

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



    }




}
