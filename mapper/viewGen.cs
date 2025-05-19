using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using System.Collections;

namespace mapper
{
    internal class viewGen
    {

        private StringBuilder sb;
        private StringBuilder loader;
       
        private StringBuilder result;
        public string API { get; set; }
        public pgDataSource ds { get; set; }


       


        private void MakeSectionType(String s)
        {
            if (s == "") return;


            DataTable tbl = ds.ReadData("select distinct field_name, comment, field_order from src_data where api ='" + API + "' and table_name = '" + s + "' order by field_order");

            string t = s.ToLower();



            StringBuilder cc = new StringBuilder();

            sb.AppendLine(" -- start " + t);
            //loader.AppendLine(" rem " + t);



            sb.AppendLine("CREATE or ALTER VIEW   c2t_"  + t + "  as select ");

            sb.AppendLine("\t\tspid ");


            loader.Append("\\COPY migration." + t + " (spid");
            //ApplicationDate, ApplicationNumber, AssetLimitPeriodEndDate, AssetLimitPeriodFlag, AssetLimitTerm, AssetLimitTermkind, BankProductID, BatchID, BranchID, Comment, CoverQuality, CredLID, CurrencyID, Date, DateClose, DateEnd, DateGrantingBKI, DateLong, DateReceptionBKI, DateSigning, DateValue, DivisionID, ExternalID, ExternalRequestID, ExtNumber, FinOperBrief, FinOperID, FlagGrantingBKI, FlagReceptionBKI, FullNumber, InDateTime, IndividualInterestRateFlag, IndividualRiskFlag, InividualPriorityRdmptFlag, LastTrancheDate, LimitDebts, LimitPayment, LimitPeriodFlag, LimitType, MainContractID, ModuleID, MultyCurFlag, Number, ParticipantID, PaymentDate, PortfolioSimilarFlag, ResPayAccNumberFlag, SPID, StateFlag, Status, TrancheDateControl, TrancheMaxTerm, TrancheMaxTermKind, UserID, UUID)FROM 'C:\Users\m.m.baranov\Documents\T-ENT\generated\CSV\baseAttr.csv' DELIMITER ';' CSV;



            int i;
            if (tbl != null)
            {
                for (i = 0; i < tbl.Rows.Count; i++)
                {

                    string f = tbl.Rows[i]["field_name"].ToString();
                    string lf = f.ToLower();
                    string c = tbl.Rows[i]["comment"].ToString();
                    c.Replace("'", " ");

                    string func = MyUtils.MapFunc(f);

                    if (func=="?" || func =="" )
                    {
                        string caser = MyUtils.MakeCase(f, c);
                        if(caser != "")
                        {
                            sb.AppendLine("\t\t," + f);
                            sb.AppendLine("\t\t," + caser);

                            loader.Append("," + lf);
                            loader.Append("," + lf +"_text");
                        }
                        else
                            sb.AppendLine("\t\t," + f);
                            loader.Append("," + lf);
                        


                    }
                    else
                    {
                        sb.AppendLine("\t\t , " + f);
                        sb.AppendLine("\t\t , dbo.c2t_" + func + "( " + f + ") " + f +"_text");

                        loader.Append("," + lf);
                        loader.Append("," + lf + "_text");

                    }

          
                }

            }



            loader.AppendLine(") FROM 'C:\\Users\\m.m.baranov\\Documents\\T-ENT\\c2t\\" + t + ".csv' DELIMITER ';' CSV;");

            sb.AppendLine(@" from " + t +@"
            go"); // end of create veiw


            sb.AppendLine(@"grant select on    c2t_" + t + @" to public 
                go ");
            sb.AppendLine(" -- end " + t);



            sb.AppendLine("");

        }



       




        public void GenerateOne()
        {
            int i;


            sb.AppendLine("-- start " + API + ";");

            DataTable tbl = ds.ReadData("select distinct table_name from src_data where for_output =1 and  api ='" + API + "' order by table_name");

            for (i = 0; i < tbl.Rows.Count; i++)
            {
                MakeSectionType(tbl.Rows[i]["table_name"].ToString());
            }

            sb.AppendLine("-- end " + API + ";");
            sb.AppendLine("");
        }


        public string GenerateAll()
        {

            result = new StringBuilder();
            sb = new StringBuilder();
            loader = new StringBuilder();
        




            //DataTable a = ds.ReadData("select distinct api from src_data where api like 'API%'");
            DataTable a = ds.ReadData("select distinct api from used_api order by api");

            int i;
            for (i = 0; i < a.Rows.Count; i++)
            {
                API = a.Rows[i]["api"].ToString();
                GenerateOne();
            }




            
            result.AppendLine(sb.ToString());


       


            result.AppendLine("");
            result.AppendLine("/* loader script ");
            result.AppendLine(loader.ToString());
            result.AppendLine("");
            result.AppendLine("*/");

            return result.ToString();


        


        }






    }
}
