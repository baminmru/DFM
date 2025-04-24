using System;
using System.Xml;
using System.Xml.Schema;
using dv21;


namespace dv21_xsd
{
	/// <summary>
	/// Summary description for XSDGen.
	/// </summary>
	public class XSDGen
	{

		private dv21.CardDefinition mcd;
		public dv21.CardDefinition cd
		{
			get
			{
				return mcd;
			}
			set 
			{
				mcd=value;
			}
		}

		public XSDGen()
		{
			mcd= null;	
		}

		public XmlSchema BuildSchema()
		{
			XmlSchema schema = new XmlSchema();
			try
			{
				XmlSchemaSimpleType GUIDType = new XmlSchemaSimpleType();
				XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
				restriction.BaseTypeName = MapBaseType("string");

				//XmlSchemaPatternFacet pf = new XmlSchemaPatternFacet();
				//pf.Value="HHHHHHHH-HHHH-HHHH-HHHH-HHHHHHHHHHHH"; "[0-9]|[a-f]|[A-F]{8}-[0-9]|[a-f]|[A-F]{4}-[0-9]|[a-f]|[A-F]{4}-[0-9]|[a-f]|[A-F]{4}-[0-9]|[a-f]|[A-F]{12}";
				//restriction.Facets.Add(pf);
				
				
				restriction.Facets.Add(XSDT.NewMaxLength(38,"")  ); 
				restriction.Facets.Add(XSDT.NewMinLength(36,"")  ); 
				GUIDType.Name ="GUID_TYPE";
				GUIDType.Content = restriction;
				schema.Items.Add(GUIDType);


				XmlSchemaComplexType RootType = new XmlSchemaComplexType() ; 


				RootType.Attributes.Add( XSDT.NewAttributeUnq ("ID" , "GUID_TYPE",XmlSchemaUse.Required  ,"Card id")); 
				XmlSchemaElement Root = XSDT.NewComplexElement( cd.Alias, RootType,cd.Name[0].Value ); 	
				schema.Items.Add(Root);

				XmlSchemaSequence _all = new XmlSchemaSequence();
				_all.MinOccurs=0;
				RootType.Particle =_all;

				int i;
				for (i=0;i<cd.Sections.Length ;i++)
				{
					XmlSchemaComplexType SectionType = MakeSectionType( cd.Sections[i],schema);
					XmlSchemaElement e=XSDT.NewSimpleElementUnq(cd.Sections[i].Alias,SectionType.Name,cd.Sections[i].Name[0].Value );
					MakeOccurs(e,cd.Sections[i].Type);
					XmlSchemaUnique element_unique = new XmlSchemaUnique();
					element_unique.Name = e.Name + "_ID";
					element_unique.Selector = new XmlSchemaXPath();
					element_unique.Selector.XPath = e.Name;
					XmlSchemaXPath field = new XmlSchemaXPath();
					field.XPath = "@ID";
					element_unique.Fields.Add(field);
					e.Constraints.Add(element_unique);
					_all.Items.Add(e);
					schema.Items.Add(SectionType); 
				}
			}
			catch
			{
			}
			return schema;
		}

		
		
		private XmlSchemaComplexType MakeSectionType( dv21.SectionType s, XmlSchema schema)
		{
			XmlSchemaComplexType SType = new XmlSchemaComplexType(); 
			SType.Name = s.Alias + "_TYPE"; 
			SType.Attributes.Add( XSDT.NewAttributeUnq("ID" ,"GUID_TYPE",XmlSchemaUse.Required  ,"row id")); 
			try
			{
				int i;
				for(i=0;i<s.Field.Length;i++)
				{
	
					XmlSchemaAttribute a;
					if (!s.Field[i].MaxSpecified && ((s.Field[i].Enum == null) || (s.Field[i].Enum.Length == 0)) )
					{
						if (s.Field[i].NotNull)
							a=XSDT.NewAttribute(s.Field[i].Alias ,XmlSchemaUse.Required  ,s.Field[i].Name[0].Value);
						else
							a=XSDT.NewAttribute(s.Field[i].Alias ,XmlSchemaUse.Optional   ,s.Field[i].Name[0].Value);

						a.SchemaTypeName =MapBaseType(s.Field[i].Type.ToString());
					} 
					else	
					{
						if (s.Field[i].Enum != null && s.Field[i].Enum.Length > 0)
						{
							a=XSDT.NewAttribute(s.Field[i].Alias ,XmlSchemaUse.Required  ,s.Field[i].Name[0].Value );
							XmlSchemaSimpleType EnumType = new XmlSchemaSimpleType();
							XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
							restriction.BaseTypeName = MapBaseType(s.Field[i].Type.ToString());
							
							int f;
							for(f=0;f<s.Field[i].Enum.Length;f++)
							{
								restriction.Facets.Add(XSDT.NewEnum(s.Field[i].Enum[f].Name,s.Field[i].Enum[f].Name)); 
							}
							EnumType.Content = restriction;
							a.SchemaType= EnumType;
						}
						else //s.Field[i].MaxSpecified
						{
							a=XSDT.NewAttribute(s.Field[i].Alias ,XmlSchemaUse.Required  ,s.Field[i].Name[0].Value);
							XmlSchemaSimpleType SizeType = new XmlSchemaSimpleType();
							XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
							restriction.BaseTypeName = MapBaseType(s.Field[i].Type.ToString());
							restriction.Facets.Add(XSDT.NewMaxLength(s.Field[i].Max,"")  ); 
							SizeType.Content = restriction;
							a.SchemaType= SizeType;
						}
					}
					SType.Attributes.Add(a); 
				}
				if (s.Section != null)
				{
					if (s.Section.Length > 0 || s.Type == dv21.SectionTypeType.tree)
					{
						XmlSchemaSequence _all = new XmlSchemaSequence();
						SType.Particle = _all;
						_all.MinOccurs = 0;
						XmlSchemaElement e;

						if (s.Type == dv21.SectionTypeType.tree)
						{
							e = XSDT.NewElement(s.Alias, s.Name[0].Value + "( as tree subitem )");
							e.SchemaTypeName = MapBaseType(s.Alias + "_TYPE");
							MakeOccurs(e, s.Type);
							XmlSchemaUnique element_unique = new XmlSchemaUnique();
							element_unique.Name = e.Name + "_ID";
							element_unique.Selector = new XmlSchemaXPath();
							element_unique.Selector.XPath = e.Name;
							XmlSchemaXPath field = new XmlSchemaXPath();
							field.XPath = "@ID";
							element_unique.Fields.Add(field);
							e.Constraints.Add(element_unique);
							_all.Items.Add(e);
						}

						for (i = 0; i < s.Section.Length; i++)
						{
							XmlSchemaComplexType SectionType = MakeSectionType(s.Section[i], schema);
							e = XSDT.NewSimpleElementUnq(s.Section[i].Alias, SectionType.Name, s.Section[i].Name[0].Value);
							MakeOccurs(e, s.Section[i].Type);
							_all.Items.Add(e);
							schema.Items.Add(SectionType);
						}
					}
				}
			}
			catch
			{
			}
			
			return SType;

		}

		private void MakeOccurs(XmlSchemaElement e, dv21.SectionTypeType type)
		{
			e.MinOccurs =0;
			if(type == dv21.SectionTypeType.@struct  )
			{
				e.MaxOccurs =1;
			}
			else
				if(type == dv21.SectionTypeType.coll )
			{
				e.MaxOccursString ="unbounded";
			}
			else
				if(type == dv21.SectionTypeType.tree  )
			{
				e.MaxOccursString ="unbounded";
			}

		}


		private XmlQualifiedName MapBaseType(string dv21Type)
		{
			switch(dv21Type)
			{
				case "int":
					return new XmlQualifiedName("int",XSDT.nsn );

				case "bool":
					return new XmlQualifiedName("boolean",XSDT.nsn );
					
				case "datetime":
					return new XmlQualifiedName("dateTime",XSDT.nsn );
					
				case "enum":
					return new XmlQualifiedName("string",XSDT.nsn );
					
				case "bitmask":
					return new XmlQualifiedName("unsignedLong",XSDT.nsn );
					
				case "uniqueid":
					return new XmlQualifiedName("GUID_TYPE","" );
					
				case "userid":
					return new XmlQualifiedName("string",XSDT.nsn );
					
				case "string":
					return new XmlQualifiedName("string",XSDT.nsn );
					
				case "unistring":
					return new XmlQualifiedName("string",XSDT.nsn );
					
				case "fileid":
					return new XmlQualifiedName("GUID_TYPE","" );
					
				case "float":
					return new XmlQualifiedName("decimal",XSDT.nsn );
					
			}
			return new XmlQualifiedName(dv21Type,"" );
		}
	}


	
	public class XSDT
	{
		public const string nsn =   "http://www.w3.org/2001/XMLSchema";

		public static void Annotate (XmlSchemaAnnotated item, string documentation)
		{
			if (documentation !="")
			{
				item.Annotation = new XmlSchemaAnnotation();
				XmlSchemaDocumentation doc =  new XmlSchemaDocumentation();
				item.Annotation.Items.Add(doc);  
				XmlDocument xdoc = new XmlDocument();
				doc.Markup=new  XmlNode[1] { xdoc.CreateTextNode(documentation)};
			}

		}
		public static XmlSchemaEnumerationFacet NewEnum(string Value, string documentation)
		{
			XmlSchemaEnumerationFacet enumeration = new XmlSchemaEnumerationFacet();
			enumeration.Value = Value;
			Annotate(enumeration,documentation);
			return enumeration;

		}

		public static XmlSchemaLengthFacet  NewLength(int Value, string documentation)
		{
			XmlSchemaLengthFacet enumeration = new XmlSchemaLengthFacet();
			enumeration.Value = Value.ToString();
			Annotate(enumeration,documentation);
			return enumeration;
		}
		
		public static XmlSchemaMaxLengthFacet  NewMaxLength(int Value, string documentation)
		{
	
			XmlSchemaMaxLengthFacet enumeration = new XmlSchemaMaxLengthFacet();
			try
			{
				enumeration.Value = Value.ToString();
			    Annotate(enumeration,documentation);			}
			catch{}
			return enumeration;
		}

		public static XmlSchemaMinLengthFacet  NewMinLength(int Value, string documentation)
		{
			XmlSchemaMinLengthFacet enumeration = new XmlSchemaMinLengthFacet();
			enumeration.Value = Value.ToString();
			Annotate(enumeration,documentation);
			return enumeration;
		}


		public static XmlSchemaAttribute NewAttribute(string Name, string type, XmlSchemaUse use, string documentation ) 
		{
			XmlSchemaAttribute Attribute = new XmlSchemaAttribute();
			Attribute.Name = Name;
            Attribute.SchemaTypeName = new XmlQualifiedName(type, nsn);
			Attribute.Use =use;
			Annotate(Attribute,documentation);
			return Attribute;
		}

		public static XmlSchemaAttribute NewAttribute(string Name,  XmlSchemaUse use, string documentation ) 
		{
			XmlSchemaAttribute Attribute = new XmlSchemaAttribute();
			Attribute.Name = Name;
			Attribute.Use =use;
			Annotate(Attribute,documentation);
			return Attribute;
		}


		public static XmlSchemaAttribute NewRefAttribute(string Name, string Ref, XmlSchemaUse use, string documentation ) 
		{
			XmlSchemaAttribute Attribute = new XmlSchemaAttribute();
			Attribute.Name =Name;
			Attribute.RefName =new XmlQualifiedName(Ref, "");
			Attribute.Use =use;
			Annotate(Attribute,documentation);
			return Attribute;
		}

		public static XmlSchemaAttribute NewAttributeUnq(string Name, string type,XmlSchemaUse use, string documentation ) 
		{
			XmlSchemaAttribute Attribute = new XmlSchemaAttribute();
			Attribute.Name = Name;
			Attribute.SchemaTypeName = new XmlQualifiedName(type, "");
			Attribute.Use = use;
			Annotate(Attribute,documentation);
			return Attribute;
		}

		public static XmlSchemaElement NewSimpleElement(string Name, string type, string documentation ) 
		{
			XmlSchemaElement Element = new XmlSchemaElement();
			Element.Name = Name;
			Element.SchemaTypeName = new XmlQualifiedName(type, nsn);
			Annotate(Element,documentation);
			return Element;
		}
		
		public static XmlSchemaElement NewSimpleElementUnq(string Name, string type, string documentation ) 
		{
			XmlSchemaElement Element = new XmlSchemaElement();
			Element.Name = Name;
			Element.SchemaTypeName = new XmlQualifiedName(type, "");
			Annotate(Element,documentation);
			return Element;
		}
		
		public static XmlSchemaElement NewRefElement(string Name, string Ref, string documentation ) 
		{
			XmlSchemaElement Element = new XmlSchemaElement();
			Element.Name = Name;
			Element.SchemaTypeName = new XmlQualifiedName(Ref , "");
			Annotate(Element,documentation);
			return Element;
		}
		
		public static XmlSchemaElement NewComplexElement(string Name, XmlSchemaType	type, string documentation ) 
		{
			XmlSchemaElement Element = new XmlSchemaElement();
			Element.Name = Name;
			Element.SchemaType = type;
			Annotate(Element,documentation);
			return Element;
		}
		
		public static XmlSchemaElement NewElement(string Name,  string documentation ) 
		{
			XmlSchemaElement Element = new XmlSchemaElement();
			Element.Name = Name;
			Annotate(Element,documentation);
			return Element;
		}
	}
}
