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
using dv21;
using dv21_load;
using System.Globalization;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace dv21_util 
{

	public class MyUtils
	{

		[STAThread]
		static void Main() 
		{
			Application.Run(new Form2());
		}


		public static MyTreeNode FindCDNode( MyTreeNode n)
		{

			MyTreeNode p = n;

            while (p != null  && p.BoundObject.GetType().ToString() != "dv21.CardDefinition")
			{
				p = (MyTreeNode)p.Parent;
			}

			return p;
        }



        public static MyTreeNode FindSectionNode(MyTreeNode n)
        {

            MyTreeNode p = n;

            while (p != null && p.BoundObject.GetType().ToString() != "dv21.SectionType")
            {
                p = (MyTreeNode)p.Parent;
            }

            return p;
        }



        public static void SerializeObject(string filename, dv21.CardDefinition cd)
		{
			try 
			{  
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.CardDefinition));
          
                // Create an XmlSerializerNamespaces object.
                XmlSerializerNamespaces ns = 
					new XmlSerializerNamespaces();


				StringWriter swriter = new StringWriter();
                serializer.Serialize(swriter, cd, ns);
				string sXML = swriter.ToString();
                swriter.Close();
                swriter=null;

                if (sXML  != "")
				{
					if (File.Exists(filename))
					{
						DateTime d = DateTime.Now;
						string fnBack = filename.Replace(".xml", "")
							+"_" + d.ToString("yyddMMHHmmss") + ".bak";

                        File.Copy(filename, fnBack, true);
					}
                    Stream fs = new FileStream(filename, FileMode.Create);
					System.Xml.XmlWriter writer =
						new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
					// Serialize using the XmlTextWriter.
					serializer.Serialize(writer, cd, ns);
					writer.Close();
					writer = null;

				}
                else
				{
					MessageBox.Show("XML backup Save Error");
				}


            
    //         
			} 
			catch ( System.Exception e)
			{
                MessageBox.Show("XML Save Error:" + e.InnerException.Message);
            }

			
		}


		public static void SerializeObject(string filename, dv21.DefFile  cd)
		{
			try 
			{  
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.DefFile));
        
                XmlSerializerNamespaces ns = 
				new XmlSerializerNamespaces();

				string sXML = "";


                using (var swriter = new StringWriter())
				{
					serializer.Serialize(swriter, cd, ns);
					sXML = swriter.ToString();
					swriter.Close();
				}

                

                if (sXML != "")
                {
                    if (File.Exists(filename))
                    {
                        DateTime d = DateTime.Now;
                        string fnBack = filename.Replace(".xml", "")
                            + "_" + d.ToString("yyddMMHHmmss") + ".bak";

                        File.Copy(filename, fnBack, true);
                    }

                    Stream fs = new FileStream(filename, FileMode.Create);
                    System.Xml.XmlWriter writer =
                        new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
                    serializer.Serialize(writer, cd, ns);
                    writer.Close();
                    writer = null;

                }
                else
                {
                    MessageBox.Show("XML backup Save Error");
                }


			
			}
            catch (System.Exception e)
            {
                MessageBox.Show("XML Save Error:" + e.Message);
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
		public static dv21.CardDefinition DeSerializeObject(string filename)
		{
			try 
			{  
					dv21.CardDefinition cd;

					// Create an instance of the XmlSerializer.
					XmlSerializer serializer = 
						new XmlSerializer(typeof(dv21.CardDefinition));
					// Reading the XML document requires a FileStream.
					Stream reader= new FileStream(filename,FileMode.Open);
          
					// Call the Deserialize method to restore the object's state.
					cd=(dv21.CardDefinition) serializer.Deserialize(reader);
					reader.Close();
					reader = null;
					return cd;
			} 
			catch
			{
					return null;
			}
		}

		public static dv21.DefFile DeSerializeLib(string filename)
		{
			try 
			{  
				dv21.DefFile cd;
				
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.DefFile));
				
				Stream reader= new FileStream(filename,FileMode.Open);
				cd=(dv21.DefFile) serializer.Deserialize(reader);
				reader.Close();
				reader = null;
				return cd;
			} 
			catch
			{
				return null;
			}
		}


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

        public static CardDefinition GetReferencedType(CardDefinition cd, string RefType)
        {

            if (cd.ID == RefType)
                return cd;

            dv21.DefFile df = null;
            CardDefinition refCD;
            try
            {
                df = MyUtils.DeSerializeLib(Application.StartupPath + "\\lib.xml");
            }
            catch
            {
            }
            int i;
            for (i = 0; i < df.Paths.Length; i++)
            {
                refCD = null;
                try
                {
                    refCD = MyUtils.DeSerializeObject(df.Paths[i].Path);
                }
                catch { }
                if (refCD != null)
                {
                    if (refCD.ID == RefType)
                        return refCD;
                }
            }
            return null;
        }


        public static SectionType GetReferencedSection(SectionType[] Sections, string RefSection)
        {
            SectionType st;
			if (Sections == null) return null;
            for (int i = 0; i < Sections.Length; i++)
            {
                st = Sections[i];
                if (st.ID == RefSection)
                    return st;
                else
                {
                    st = GetReferencedSection(st.Section, RefSection);
                    if (st != null) return st;
                }
            }

            return null;
        }


        public static string GetSectionBrief(SectionType St)
        {
            if (St == null) return "";
            if (St.Field  == null) return "";

            for (int i = 0; i < St.Field.Length; i++)
            {
                
                if (St.Field[i].IsBrief)
				{
					return St.Field[i].Alias;

                }
                    
            }

            return "";
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


    }

	public class MyTreeNode:  TreeNode 
	{
		private Object mBoundObject;
		private System.Windows.Forms.ContextMenu  mMenu;
        private String mPath;

        public System.Windows.Forms.ContextMenu NodeContextMenu               // Topic is a named parameter
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