using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;

namespace mapper
{
    internal class viewGen
    {

        private StringBuilder sb;
        private StringBuilder result;
        public string API { get; set; }
        public pgDataSource ds { get; set; }





        private void MakeSectionType(String s)
        {
            if (s == "") return;


            DataTable tbl = ds.ReadData("select * from src_data where api ='" + API + "' and table_name = '" + s + "' order by field_order");

            string t = s.ToLower();



            StringBuilder cc = new StringBuilder();

            sb.AppendLine(" -- start " + t);

           

            sb.AppendLine("CREATE or ALTER VIEW   c2t_"  + t + "(");

            sb.AppendLine("\t\tspid  DSINT_KEY ");


            int i;
            if (tbl != null)
            {
                for (i = 0; i < tbl.Rows.Count; i++)
                {

                    string f = tbl.Rows[i]["field_name"].ToString();
                    string func = MapFunc(f);

                    if (func=="?" || func =="" )
                    {

                        sb.AppendLine("\t\t," + f);

                        
                    }
                    else
                    {
                        sb.AppendLine("\t\t -- , " + f);
                        sb.AppendLine("\t\t , dbo.c2t_" + func + "( " + f + ") " + f);
                        
                    }
                }

            }





            sb.AppendLine(@");
            go"); // end of create table


            sb.AppendLine(@"grant select on    c2t_" + t + @" to public 
                go ");
            sb.AppendLine(" -- end " + t);



            sb.AppendLine("");

        }



        public string MapFunc( string f)
        {
            string func="";
            
            if (f == "DebtType") func = "debt_type";
            if (f == "ActionType") func = "action_type";


            if (f == "ValueID") func = "?";
            if (f == "BorrowerID") func = "";
            if (f == "ProductTypeID") func = "bank_product";
            if (f == "AccountCurrencyID") func = "currency";
            if (f == "ParentLoanID") func = "?";
            if (f == "DocumentID") func = "";
            if (f == "ResAmountID") func = "";
            if (f == "EscAddresseeID") func = "";
            if (f == "InterestRateID") func = "rate";
            if (f == "PeriodID") func = "";
            if (f == "UUID") func = "?";
            if (f == "FinOperID") func = "?";
            if (f == "BatchRqID") func = "?";
            if (f == "BatchID") func = "batch";
            if (f == "BatchDfnRuleID") func = "batch";
            if (f == "OffsetClnID") func = "";
            if (f == "LinkAccountID") func = "account";
            if (f == "PayDayCalendarID") func = "calendar";
            if (f == "TrancheProductTypeID") func = "";
            if (f == "DecisionRestructUserID") func = "";
            if (f == "RecipientLinkID") func = "";
            if (f == "ClassifierID") func = "";
            if (f == "ChangeRequestID") func = "";
            if (f == "BranchExtID") func = "branch";
            if (f == "PayCalendarID") func = "calendar";
            if (f == "ParentNodeID") func = "?";
            if (f == "NodeClassifierID") func = "";
            if (f == "EventKindID") func = "";
            if (f == "HeadingID") func = "";
            if (f == "PaymentInstructionRuleID") func = "?";
            if (f == "ExternalRequestID") func = "?";
            if (f == "LawsuitID") func = "?";
            if (f == "PrepaymentAccountID") func = "account";
            if (f == "SubjectKindID") func = "";
            if (f == "DaysCalendarID") func = "calendar";
            if (f == "ContractCoverID") func = "?";
            if (f == "LoanID") func = "?";
            if (f == "CredLCurrencyID") func = "currency";
            if (f == "CreditorID") func = "partner";
            if (f == "CalendarSpecID") func = "calendar";
            if (f == "RateCourseTypeID") func = "cource_type";
            if (f == "LoanFinOperID") func = "?";
            if (f == "SchemeID") func = "";
            if (f == "ObjEventID") func = "";
            if (f == "NTFID") func = "?";
            if (f == "ModuleID") func = "?";
            if (f == "PayerID") func = "";
            if (f == "BalanceID") func = "";
            if (f == "BorrowerCategoryID") func = "";
            if (f == "LinkUserTypeID") func = "";
            if (f == "CalendarID") func = "calendar";
            if (f == "ContractOverID") func = "";
            if (f == "NodeClassifierCategoryID") func = "";
            if (f == "LinkContractID") func = "?";
            if (f == "ParticipantID") func = "partner";
            if (f == "ContractCreditID") func = "";
            if (f == "AccountID") func = "account";
            if (f == "AgreementID") func = "";
            if (f == "LinkTypeID") func = "";
            if (f == "AccTypeID") func = "";
            if (f == "EmployeeID") func = "";
            if (f == "LinkID") func = "";
            if (f == "CalendarOperID") func = "calendar";
            if (f == "BranchID") func = "branch";
            if (f == "CalendarFactID") func = "calendar";
            if (f == "ParticipantAccountID") func = "account";
            if (f == "PaymentID") func = "";
            if (f == "ValLookupObjID") func = "";
            if (f == "EvtControlID") func = "";
            if (f == "FixedAmountCurrencyID") func = "";
            if (f == "SchemeRedemptionID") func = "";
            if (f == "DebtCurrencyID") func = "";
            if (f == "SubjectLinkID") func = "";
            if (f == "EvtAssigneeID") func = "";
            if (f == "DecisionID") func = "?";
            if (f == "BankProductID") func = "";
            if (f == "ObjectID") func = "?";
            if (f == "InstitutionAccountID") func = "account";
            if (f == "PayCorrCalendarID") func = "calendar";
            if (f == "TrancheID") func = "?";
            if (f == "UnderwriterID") func = "";
            if (f == "SubcontoID") func = "";
            if (f == "GUID") func = "?";
            if (f == "GroupAddAccID") func = "";
            if (f == "CourseTypeID") func = "cource_type";
            if (f == "ManagerID") func = "user";
            if (f == "NodeID") func = "?";
            if (f == "ActionTypeID") func = "action_type";
            if (f == "RegTypeID") func = "";
            if (f == "ProtocolID") func = "?";
            if (f == "TermCalendarID") func = "calendar";
            if (f == "PrepaymentRequestID") func = "";
            if (f == "GroupID") func = "?";
            if (f == "CloseReasonID") func = "";
            if (f == "DivisionID") func = "branch";
            if (f == "RecipientID") func = "partner";
            if (f == "BankParticipantID") func = "";
            if (f == "FormulaID") func = "";
            if (f == "BaseInterestRateID") func = "";
            if (f == "RqReserveRestID") func = "";
            if (f == "CredLProductTypeID") func = "bank_product";
            if (f == "EIRControlRateID") func = "";
            if (f == "DirectionID") func = "";
            if (f == "TrancheAgreementID") func = "";
            if (f == "UserID") func = "user";
            if (f == "ActionID") func = "action_type";
            if (f == "AimKindID") func = "aim";
            if (f == "ContractBatchID") func = "";
            if (f == "CreditBankProductID") func = "bank_product";
            if (f == "LimitID") func = "";
            if (f == "ConvCurrencyID") func = "currency";
            if (f == "AimID") func = "aim";
            if (f == "StateID") func = "";
            if (f == "PocketID") func = "";
            if (f == "LoanCtrRelationID") func = "";
            if (f == "ContractID") func = "?";
            if (f == "IQClsID") func = "";
            if (f == "CCredID") func = "?";
            if (f == "RequestID") func = "";
            if (f == "SchemePeriodID") func = "";
            if (f == "BankID") func = "partner";
            if (f == "AccrualID") func = "";
            if (f == "CredLAgreementID") func = "";
            if (f == "InterestID") func = "interest";
            if (f == "CredLID") func = "";
            if (f == "PaymentInstructionID") func = "";
            if (f == "PaymentInstructionTypeID") func = "";
            if (f == "CorrespondentID") func = "";
            if (f == "EarlyPayMinCurrencyID") func = "";
            if (f == "CenterLiabilityID") func = "";
            if (f == "MainContractID") func = "";
            if (f == "InstitutionID") func = "partner";
            if (f == "InspectorID") func = "";
            if (f == "ExternalID") func = "";
            if (f == "CurrencyID") func = "currency";
            if (f == "ClientID") func = "partner";
            if (f == "AuditID") func = "";
            if (f == "DocFinOperID") func = "";
            if (f == "BankPartnerID") func = "partner";
            if (f == "MemberID") func = "";
            if (f == "BankAccountID") func = "account";


            return func;
        }




        public void GenerateOne()
        {
            int i;


            sb.AppendLine("-- start " + API + ";");

            DataTable tbl = ds.ReadData("select distinct table_name from src_data where api ='" + API + "'");

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

           


           
            DataTable a = ds.ReadData("select distinct api from src_data where api like 'API%'");

            int i;
            for (i = 0; i < a.Rows.Count; i++)
            {
                API = a.Rows[i]["api"].ToString();
                GenerateOne();
            }




            
            result.AppendLine(sb.ToString());
            
            return result.ToString();


        


        }






    }
}
