using System;
using System.Data;
using System.IO;
using System.Xml.Serialization ;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters;  
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Globalization;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Text.Encodings.Web;
using System.Text.Unicode;
using System.Xml;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.TextBox;

namespace mapper
{

	public class MyUtils
	{

		//public static MyTreeNode FindCDNode( MyTreeNode n)
		//{

		//	MyTreeNode p = n;

  //          while (p != null  && p.BoundObject.GetType().ToString() != "dv21.CardDefinition")
		//	{
		//		p = (MyTreeNode)p.Parent;
		//	}

		//	return p;
  //      }



  //      public static MyTreeNode FindSectionNode(MyTreeNode n)
  //      {

  //          MyTreeNode p = n;

  //          while (p != null && p.BoundObject.GetType().ToString() != "dv21.SectionType")
  //          {
  //              p = (MyTreeNode)p.Parent;
  //          }

  //          return p;
  //      }

        public static TreeNode SyncToNode(TreeNodeCollection root, object obj)
        {
            MyTreeNode m; int i; TreeNode result;

            for (i = 0; i < root.Count; i++)
            {
                m = (MyTreeNode)root[i];
                if (m.BoundObject == obj)
                {
                    result = m;
                    return result;
                }
                result = SyncToNode(m.Nodes, obj);
                if (result != null) return result;
            }
            return null;

        }


        public static void CollectExpanded(List<string> expandedFrom, TreeNodeCollection nodes)
        {
            foreach (TreeNode node in nodes)
            {
                if (node.IsExpanded) expandedFrom.Add(node.FullPath);
                if (node.Nodes.Count > 0) CollectExpanded(expandedFrom, node.Nodes);
            }
        }



        public static void ExpandNodes(TreeNode node, string nodeFullPath)
        {
            if (node.FullPath == nodeFullPath) node.Expand();
            foreach (TreeNode n in node.Nodes)
            {
                if (n.Nodes.Count > 0) ExpandNodes(n, nodeFullPath);
            }
        }


        


  //      public static void SerializeObject(string filename, dv21.CardDefinition cd)
		//{
		//	try 
		//	{  
		//		XmlSerializer serializer = 
		//			new XmlSerializer(typeof(dv21.CardDefinition));
          
  //              // Create an XmlSerializerNamespaces object.
  //              XmlSerializerNamespaces ns = 
		//			new XmlSerializerNamespaces();


		//		StringWriter swriter = new StringWriter();
  //              serializer.Serialize(swriter, cd, ns);
		//		string sXML = swriter.ToString();
  //              swriter.Close();
  //              swriter=null;

  //              if (sXML  != "")
		//		{
		//			if (File.Exists(filename))
		//			{
		//				DateTime d = DateTime.Now;
		//				//string fnBack = filename.Replace(".xml", "")
		//				//	+"_" + d.ToString("yyddMMHHmmss") + ".bak";


  //                      string fnBack = filename + ".bak";

  //                      File.Copy(filename, fnBack, true);
		//			}
  //                  Stream fs = new FileStream(filename, FileMode.Create);
		//			System.Xml.XmlWriter writer =
		//				new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
		//			// Serialize using the XmlTextWriter.
		//			serializer.Serialize(writer, cd, ns);
		//			writer.Close();
		//			writer = null;

		//		}
  //              else
		//		{
		//			MessageBox.Show("XML backup Save Error");
		//		}


            
  //  //         
		//	} 
		//	catch ( System.Exception e)
		//	{
  //              MessageBox.Show("XML Save Error:" + e.InnerException.Message);
  //          }

			
		//}



  //      public static void SerializeObjectToJSON(string filename, dv21.CardDefinition cd)
  //      {
  //          try
  //          {

  //              var options = new JsonSerializerOptions
  //              {
  //                  //Encoder = JavaScriptEncoder.Create(UnicodeRanges.BasicLatin, UnicodeRanges.Cyrillic),
  //                  Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping,
  //                  WriteIndented = true
  //              };
  //              string sJSON = JsonSerializer.Serialize<dv21.CardDefinition>( cd, options);
                

  //              if (sJSON != "")
  //              {
  //                  if (File.Exists(filename))
  //                  {
  //                      DateTime d = DateTime.Now;
  //                      //string fnBack = filename.Replace(".json", "")
  //                      //    + "_" + d.ToString("yyddMMHHmmss") + ".json";

  //                      string fnBack = filename + ".bak";

  //                      File.Copy(filename, fnBack, true);
  //                  }

  //                  File.WriteAllText(filename, sJSON);

  //              }
  //              else
  //              {
  //                  MessageBox.Show("JSON backup Save Error");
  //              }
  //          }
  //          catch (System.Exception e)
  //          {
  //              MessageBox.Show("JSON Save Error:" + e.InnerException.Message);
  //          }


  //      }


  //      public static void SerializeObjectToJSON(string filename, dv21.DefFile df)
  //      {
  //          try
  //          {

  //              var options = new JsonSerializerOptions
  //              {
  //                  //Encoder = JavaScriptEncoder.Create(UnicodeRanges.BasicLatin, UnicodeRanges.Cyrillic),
  //                  Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping,
  //                  WriteIndented = true
  //              };

  //              string sJSON =   JsonSerializer.Serialize<dv21.DefFile>(df, options);
        


  //              if (sJSON != "")
  //              {
  //                  if (File.Exists(filename))
  //                  {
  //                      DateTime d = DateTime.Now;
  //                      string fnBack = filename+ ".bak";
  //                      //+"_" + d.ToString("yyddMMHHmmss") + ".json.bak";

  //                      File.Copy(filename, fnBack, true);
  //                  }

  //                  File.WriteAllText(filename, sJSON);

  //              }
  //              else
  //              {
  //                  MessageBox.Show("JSON backup Save Error");
  //              }
  //          }
  //          catch (System.Exception e)
  //          {
  //              MessageBox.Show("JSON Save Error:" + e.InnerException.Message);
  //          }


  //      }



  //      public static void SerializeObject(string filename, dv21.DefFile  cd)
		//{
		//	try 
		//	{  
		//		XmlSerializer serializer = 
		//			new XmlSerializer(typeof(dv21.DefFile));
        
  //              XmlSerializerNamespaces ns = 
		//		new XmlSerializerNamespaces();

		//		string sXML = "";


  //              using (var swriter = new StringWriter())
		//		{
		//			serializer.Serialize(swriter, cd, ns);
		//			sXML = swriter.ToString();
		//			swriter.Close();
		//		}

                

  //              if (sXML != "")
  //              {
  //                  if (File.Exists(filename))
  //                  {
  //                      DateTime d = DateTime.Now;
  //                      //string fnBack = filename.Replace(".xml", "")
  //                      //    + "_" + d.ToString("yyddMMHHmmss") + ".bak";

  //                      string fnBack = filename + ".bak";

  //                      File.Copy(filename, fnBack, true);
  //                  }

  //                  Stream fs = new FileStream(filename, FileMode.Create);
  //                  System.Xml.XmlWriter writer =
  //                      new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
  //                  serializer.Serialize(writer, cd, ns);
  //                  writer.Close();
  //                  writer = null;

  //              }
  //              else
  //              {
  //                  MessageBox.Show("XML backup Save Error");
  //              }


			
		//	}
  //          catch (System.Exception e)
  //          {
  //              MessageBox.Show("XML Save Error:" + e.Message);
  //          }
  //      }


        



        public static void Tool_WriteFile(string s, string path, string fname, bool Capitalize = false)
        {
            string p = path;
            if (!p.EndsWith("\\"))
            {
                p += "\\";
            }
            DirectoryInfo di = new DirectoryInfo(p);

            if (!di.Exists)
            {
                di.Create();
            }
            File.WriteAllText(p + fname, s, System.Text.Encoding.UTF8);
           
            
        }


        public static string DeCap(string s)
        {
            //if (!UseDeCap)
            //{
            //    return s;
            //}

            string sOut;
            if (!string.IsNullOrEmpty(s))
            {
                sOut = char.ToLower(s[0]) + s.Substring(1);
                return sOut;
            }
            else
            {
                return s;
            }
        }


        /*
                public static void SerializeObject(string filename, dv21_list.CardDefinition cd)
                {
                    try 
                    {  
                        XmlSerializer serializer = 
                            new XmlSerializer(typeof(dv21_list.CardDefinition));

                        // Create an XmlSerializerNamespaces object.
                        XmlSerializerNamespaces ns = 
                            new XmlSerializerNamespaces();
                        // Add two namespaces with prefixes.
                        //ns.Add("inventory", "http://www.cpandl.com");
                        //ns.Add("money", "http://www.cohowinery.com");

                        // Create an XmlTextWriter using a FileStream.
                        Stream fs = new FileStream(filename, FileMode.Create);
                        System.Xml.XmlWriter writer = 
                            new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
                        // Serialize using the XmlTextWriter.
                        serializer.Serialize(writer, cd, ns);
                        writer.Close();
                        writer=null;
                    } 
                    catch
                    {
                    }
                }


                public static dv21_list.CardDefinition ArrayToList(dv21.CardDefinition FromObject)
                {
                    dv21_list.CardDefinition ToObject;
                    Stream stm= new MemoryStream();


                    try 
                    {  
                        XmlSerializer serializer1 = 
                            new XmlSerializer(typeof(dv21.CardDefinition));

                        XmlSerializerNamespaces ns = 
                            new XmlSerializerNamespaces();

                        System.Xml.XmlWriter writer = 
                            new System.Xml.XmlTextWriter(stm, new System.Text.UTF8Encoding());
                        serializer1.Serialize(writer, FromObject, ns);
                        stm.Position    =0;
                        XmlSerializer serializer2 = 
                            new XmlSerializer(typeof(dv21_list.CardDefinition));
                        ToObject=(dv21_list.CardDefinition) serializer2.Deserialize(stm);
                        stm.Close();
                        stm = null;
                    }
                    catch{ ToObject=null; }
                    return ToObject;
                }


                public static dv21.CardDefinition ListToArray(dv21_list.CardDefinition FromObject )
                {
                    dv21.CardDefinition ToObject;
                    Stream stm= new MemoryStream();


                    try 
                    {  
                        XmlSerializer serializer1 = 
                            new XmlSerializer(typeof(dv21_list.CardDefinition));

                        XmlSerializerNamespaces ns = 
                            new XmlSerializerNamespaces();

                        System.Xml.XmlWriter writer = 
                            new System.Xml.XmlTextWriter(stm, new System.Text.UTF8Encoding());
                        serializer1.Serialize(writer, FromObject, ns);
                        stm.Position    =0;
                        XmlSerializer serializer2 = 
                            new XmlSerializer(typeof(dv21.CardDefinition));
                        ToObject=(dv21.CardDefinition) serializer2.Deserialize(stm);
                        stm.Close();
                        stm = null;
                    }
                    catch{ToObject=null;}
                    return ToObject;
                }
        */
  //      public static dv21.CardDefinition DeSerializeObject(string filename)
		//{
		//	try 
		//	{  
		//			dv21.CardDefinition cd;

		//			// Create an instance of the XmlSerializer.
		//			XmlSerializer serializer = 
		//				new XmlSerializer(typeof(dv21.CardDefinition));
		//			// Reading the XML document requires a FileStream.
		//			Stream reader= new FileStream(filename,FileMode.Open);
          
		//			// Call the Deserialize method to restore the object's state.
		//			cd=(dv21.CardDefinition) serializer.Deserialize(reader);
		//			reader.Close();
		//			reader = null;
		//			return cd;
		//	} 
		//	catch
		//	{
		//			return null;
		//	}
		//}

  //      public static dv21.CardDefinition DeSerializeObjectFromJSON(string filename)
  //      {
  //          try
  //          {
  //              ;


  //              string jsonString = File.ReadAllText(filename);
  //              dv21.CardDefinition cd = JsonSerializer.Deserialize<dv21.CardDefinition>(jsonString);
                
  //              return cd;
  //          }
  //          catch
  //          {
  //              return null;
  //          }
  //      }


  //      public static dv21.DefFile DeSerializeLib(string filename)
		//{
		//	try 
		//	{  
		//		dv21.DefFile cd;
				
		//		XmlSerializer serializer = 
		//			new XmlSerializer(typeof(dv21.DefFile));
				
		//		Stream reader= new FileStream(filename,FileMode.Open);
		//		cd=(dv21.DefFile) serializer.Deserialize(reader);
		//		reader.Close();
		//		reader = null;
		//		return cd;
		//	} 
		//	catch
		//	{
		//		return null;
		//	}
		//}

  //      public static dv21.DefFile DeSerializeLibFromJSON(string filename)
  //      {
  //          try
  //          {
  //              dv21.DefFile cd;
  //              cd = JsonSerializer.Deserialize< dv21.DefFile>(filename);
  //              return cd;
  //          }
  //          catch
  //          {
  //              return null;
  //          }
  //      }




        /*		public static dv21_list.CardDefinition DeSerializeObject2(string filename)
                {
                    try 
                    {  
                        dv21_list.CardDefinition cd;

                        // Create an instance of the XmlSerializer.
                        XmlSerializer serializer = 
                            new XmlSerializer(typeof(dv21_list.CardDefinition));
                        // Reading the XML document requires a FileStream.
                        Stream reader= new FileStream(filename,FileMode.Open);

                        // Call the Deserialize method to restore the object's state.
                        cd=(dv21_list.CardDefinition) serializer.Deserialize(reader);
                        reader.Close();
                        reader = null;
                        return cd;
                    } 
                    catch
                    {
                        return null;
                    }
                }
                */


        public static Array Add(Array arr, Object Item,Array arr2)
		{
			if(arr !=null)
			{
				ArrayList lsa = new ArrayList();
				lsa.InsertRange(0,arr);
				lsa.Add(Item);
				lsa.CopyTo(arr2,0);
				lsa=null;
			}
			return arr2;
		}


        public static T[] AddAt<T>(T[] arr, int Index, T Item, T[] arr2)
        {
            if (arr != null)
            {
                if (Index < arr.Length)
                {


                    for (int i = 0; i < arr.Length; i++)
                    {
                        if (i <Index )
                        {
                            arr2[i] = arr[i];
                        }

                        if (i >= Index)
                        {
                            arr2[i+1] = arr[i];
                        }

                        
                    }
                    arr2[Index] = Item;

                }
                else
                {
                    for (int i = 0; i < arr.Length; i++)
                    {
                        arr2[i] = arr[i];
                    }
                    arr2[arr.Length] = Item;
                }
                
            }
            return arr2;
        }



        public static T[] MoveUp<T>(T[] arr, int Index)
        {
            if (arr != null)
            {
                if (Index > 0 && Index <= arr.Length)
                {

                    List<T> arr2 = new List<T>();

                    T Item = arr[Index];
                    T Prev = arr[Index-1];

                    for (int i = 0; i < Index - 1; i++)
                    {
                        arr2.Add(arr[i]);
                    }

                    arr2.Add(Item);
                    arr2.Add(Prev);

                    for (int i = Index+1; i < arr.Length; i++)
                    {
                        arr2.Add(arr[i]);
                    }
                    return arr2.ToArray();
                }

            }
            return null;
        }

        public static T[] MoveDn<T>(T[] arr, int Index)
        {
            if (arr != null)
            {
                if (Index >= 0 && Index < arr.Length - 1)
                {

                    List<T> arr2 = new List<T>();

                    T Item = arr[Index];
                    T nextItem = arr[Index+1];

                    for (int i = 0; i <= Index - 1; i++)
                    {
                        arr2.Add(arr[i]);
                    }

                    arr2.Add(nextItem);
                    arr2.Add(Item);
                    

                    for (int i = Index + 2; i < arr.Length; i++)
                    {
                        arr2.Add(arr[i]);
                    }
                    return arr2.ToArray();
                }

            }
            return null;
        }



        public static Array RemoveAt(Array arr, int Index, Array arr2)
		{
			if(arr !=null)
			{
				ArrayList lsa = new ArrayList();
				lsa.InsertRange(0,arr);
				lsa.RemoveAt(Index);
				lsa.CopyTo(arr2,0);
				lsa=null;
			}
			return arr2;
		}

		public static Array Remove(Array arr, Object Item, Array arr2)
		{
			if(arr !=null)
			{
				ArrayList lsa = new ArrayList();
				lsa.InsertRange(0,arr);
				lsa.Remove(Item);
				lsa.CopyTo(arr2,0);
				lsa=null;
			}
			return arr2;
		}



      


        public static string C1(string title)
        {

			//return CultureInfo.CurrentCulture.TextInfo.ToTitleCase(title.Replace("_",""));
			if (title == null) return "XXX";

            StringBuilder result = new StringBuilder(title.Length);
            bool makeUpper = true;
            foreach (var c in title)
            {
				if (makeUpper)
				{
					result.Append(Char.ToUpper(c));
					makeUpper = false;
				}
				else
				{
					if (c == '_')
					{
						makeUpper = true;
					}
					else
					{
						result.Append(c);
					}
				}
            }
			return result.ToString();

        }


        public static string C2(string title)
        {

            
            if (title == null) return "XXX";

            StringBuilder result = new StringBuilder(title.Length);
            bool makeUpper = false;
            foreach (var c in title)
            {
                if (makeUpper)
                {
                    result.Append(Char.ToUpper(c));
                    makeUpper = false;
                }
                else
                {
                    if (c == '_')
                    {
                        makeUpper = true;
                    }
                    else
                    {
                        result.Append(c);
                    }
                }
            }
            return result.ToString();

        }



        public static string CropComment(string comment)
        {


            if (comment == null) return "?";

            if (comment.Length < 50)
                return comment;

            StringBuilder result = new StringBuilder(comment.Length);



            // try to crop only first sentence          
            foreach (var c in comment)
            {

                if (c == '.' || c == ',' )
                {
                    result.Append(c);
                    return result.ToString();
                }
                else
                {
                    result.Append(c);
                }
            }

            return comment.Substring(0, 50);

        }


        public static string CatCR(string comment)
        {


            if (comment == null) return "";

            
            string sOut = comment.Replace("\r"," ");
            sOut = sOut.Replace("\n", " ");
            sOut = sOut.Replace("\t", " ");
            return sOut;
        }



        public static string MapFunc(string f)
        {
            string func = "";

            
            if (f == "ValueID") func = "";
            if (f == "BorrowerID") func = "";
            if (f == "ProductTypeID") func = "bank_product";
            if (f == "AccountCurrencyID") func = "currency";
            if (f == "ParentLoanID") func = "";
            if (f == "DocumentID") func = "";
            if (f == "ResAmountID") func = "";
            if (f == "EscAddresseeID") func = "";
            if (f == "InterestRateID") func = "";
            if (f == "PeriodID") func = "";
            if (f == "UUID") func = "";
            if (f == "FinOperID") func = "";
            if (f == "BatchRqID") func = "";
            if (f == "BatchID") func = "";
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
            if (f == "ParentNodeID") func = "";
            if (f == "NodeClassifierID") func = "";
            if (f == "EventKindID") func = "";
            if (f == "HeadingID") func = "";
            if (f == "PaymentInstructionRuleID") func = "";
            if (f == "ExternalRequestID") func = "";
            if (f == "LawsuitID") func = "";
            if (f == "PrepaymentAccountID") func = "account";
            if (f == "SubjectKindID") func = "";
            if (f == "DaysCalendarID") func = "calendar";
            if (f == "ContractCoverID") func = "";
            if (f == "LoanID") func = "";
            if (f == "CredLCurrencyID") func = "currency";
            if (f == "CreditorID") func = "partner";
            if (f == "CalendarSpecID") func = "calendar";
            if (f == "RateCourseTypeID") func = "cource_type";
            if (f == "LoanFinOperID") func = "";
            if (f == "SchemeID") func = "";
            if (f == "ObjEventID") func = "";
            if (f == "NTFID") func = "";
            if (f == "ModuleID") func = "";
            if (f == "PayerID") func = "";
            if (f == "BalanceID") func = "";
            if (f == "BorrowerCategoryID") func = "";
            if (f == "LinkUserTypeID") func = "";
            if (f == "CalendarID") func = "calendar";
            if (f == "ContractOverID") func = "";
            if (f == "NodeClassifierCategoryID") func = "";
            if (f == "LinkContractID") func = "";
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
            if (f == "DecisionID") func = "";
            if (f == "BankProductID") func =  "bank_product";
            if (f == "ObjectID") func = "";
            if (f == "InstitutionAccountID") func = "account";
            if (f == "PayCorrCalendarID") func = "calendar";
            if (f == "TrancheID") func = "";
            if (f == "UnderwriterID") func = "";
            if (f == "SubcontoID") func = "";
            if (f == "GUID") func = "";
            if (f == "GroupAddAccID") func = "";
            if (f == "CourseTypeID") func = "cource_type";
            if (f == "ManagerID") func = "user";
            if (f == "NodeID") func = "";
            if (f == "ActionTypeID") func = "action_type";
            if (f == "RegTypeID") func = "";
            if (f == "ProtocolID") func = "";
            if (f == "TermCalendarID") func = "calendar";
            if (f == "PrepaymentRequestID") func = "";
            if (f == "GroupID") func = "";
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
            if (f == "ContractID") func = "";
            if (f == "IQClsID") func = "";
            if (f == "CCredID") func = "";
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



        public static string MakeCase(string f, string c)
        {


            StringBuilder caser;
            caser = new StringBuilder();

            Dictionary<int, string> items = new Dictionary<int, string>();

            string[] spl;
            char[] sep0 = { 'Ц', '-', 'Ц' };
            spl = c.Split(sep0);

            if (spl.Length > 2)
            {
                for (int i = 1; i < spl.Length; i++)
                {
                    string[] prev;
                    char[] sep = { ' ', ';', ':', '.', ',', '_' };
                    prev = spl[i - 1].Split(sep);

                    int n = 0;
                    int e;

                    for (e = prev.Length - 1; e >= 0; e--)
                    {
                        if (prev[e] != "")
                            break;
                    }

                    bool isNumeric = false;

                    if (e >= 0 && e < prev.Length)
                        isNumeric = int.TryParse(prev[e], out n);

                    if (isNumeric)
                    {
                        string name = "";

                        string[] cur;
                        cur = spl[i].Split(sep);

                        int z;


                        for (z = cur.Length - 1; z >= 0; z--)
                        {
                            if (cur[z] != "")
                                break;
                        }


                        if (i < spl.Length - 1)
                        {
                            for (int j = 0; j < z; j++)
                            {
                                name = name + ' ' + cur[j];
                            }
                        }
                        else
                        {
                            for (int j = 0; j <= z; j++)
                            {
                                name = name + ' ' + cur[j];
                            }
                        }

                        name = name.Replace("(по умолчанию)", "");

                        char[] brc = { '(', ')', '{', '}', '[', ']' };
                        string[] nobrc = name.Split(brc);

                        if (nobrc.Length > 2)
                            name = nobrc[0];



                        if (!items.Keys.Contains(n))
                            items.Add(n, name);
                    }

                }

                if (items.Keys.Count > 1)
                {


                    caser.AppendLine("case   " + f);
                    foreach (int key in items.Keys)
                    {
                        caser.AppendLine("\t\t\t when " + key.ToString() + " then '" + items[key].TrimStart().TrimEnd() + "'");

                    }
                    caser.AppendLine("\t\t\t else  convert(nvarchar(max)," + f + "_text) ");
                    caser.AppendLine("\t\tend " + f);

                }

            }
            return caser.ToString();
        }



    }

	public class MyTreeNode:  TreeNode 
	{
		private Object mBoundObject;
		private System.Windows.Forms.ContextMenuStrip  mMenu;
        private String mPath;

        public System.Windows.Forms.ContextMenuStrip NodeContextMenu               // Topic is a named parameter
		{
			get 
			{ 
				return mMenu; 
			}
			set 
			{ 

				mMenu = value; 
			}
		}


        public String Path               
        {
            get
            {
                return mPath;
            }
            set
            {

                mPath = value;
            }
        }

        public object BoundObject               // Topic is a named parameter
		{
			get 
			{ 
				return mBoundObject; 
			}
			set 
			{ 

				mBoundObject = value; 
			}
		}


		public MyTreeNode():base()
		{}

		public MyTreeNode(string s):base(s)
		{}
		
		public MyTreeNode(string s, int ImageIndex, int SelIndex):base(s,ImageIndex,SelIndex)
		{}

      

    }

}
