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

namespace dv21_util 
{

	public class MyUtils
	{

		[STAThread]
		static void Main() 
		{
			Application.Run(new Form2());
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


		public static void SerializeObject(string filename, dv21.DefFile  cd)
		{
			try 
			{  
				XmlSerializer serializer = 
					new XmlSerializer(typeof(dv21.DefFile));
		
				XmlSerializerNamespaces ns = 
				new XmlSerializerNamespaces();
				Stream fs = new FileStream(filename, FileMode.Create);
				System.Xml.XmlWriter writer = 
					new System.Xml.XmlTextWriter(fs, new System.Text.UTF8Encoding());
				serializer.Serialize(writer, cd, ns);
				writer.Close();
				writer=null;
			} 
			catch
			{
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

    }

	public class MyTreeNode:  TreeNode 
	{
		private Object mBoundObject;
		private System.Windows.Forms.ContextMenu  mMenu;

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