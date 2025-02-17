using System;
using System.Collections ;

namespace dv21_list
{
	using System.Xml.Serialization;
    
    
	/// <remarks/>
	[System.Xml.Serialization.XmlRootAttribute(Namespace="", IsNullable=false)]
	public class CardDefinition 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("Action", IsNullable=false)]
		public ArrayList  Actions;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("Mode", IsNullable=false)]
		public ArrayList Modes;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ListOfLocalizedString Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("Section", IsNullable=false)]
		public ArrayList Sections;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("ViewElement", IsNullable=false)]
		public ArrayList ViewElements;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string CategoryID;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Alias;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Version;
	}
    
	/// <remarks/>
	public class ActionType 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ArrayList Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
	}


    
	/// <remarks/>
	public class LocalizedStringsLocalizedString 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, DataType="language")]
		public string Language;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlTextAttribute()]
		public string Value;
	}
    
	/// <remarks/>
	public class ViewColumnType 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ArrayList Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Alias;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
	}
    
	/// <remarks/>
	public class ViewElementType 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ArrayList Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("Column", IsNullable=false)]
		public ArrayList Columns;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool Default;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool DefaultSpecified;
	}
    
	/// <remarks/>
	public class FieldTypeEnum 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public int Value;
	}
    
	/// <remarks/>
	public class FieldType 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ArrayList Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("Enum")]
		public ArrayList Enum;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Alias;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public FieldTypeType Type;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public int Max;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool MaxSpecified;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool NotNull;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool NotNullSpecified;
	}
    
	/// <remarks/>
	public enum FieldTypeType 
	{
        
		/// <remarks/>
		@int,
        
		/// <remarks/>
		@bool,
        
		/// <remarks/>
		datetime,
        
		/// <remarks/>
		@enum,
        
		/// <remarks/>
		bitmask,
        
		/// <remarks/>
		uniqueid,
        
		/// <remarks/>
		userid,
        
		/// <remarks/>
		@string,
        
		/// <remarks/>
		unistring,
        
		/// <remarks/>
		fileid,
        
		/// <remarks/>
		@float,
	}
    
	/// <remarks/>
	public class SectionType 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ArrayList Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("Field")]
		public ArrayList Field;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("Section")]
		public ArrayList Section;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Alias;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public SectionTypeType Type;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
	}
    
	/// <remarks/>
	public enum SectionTypeType 
	{
        
		/// <remarks/>
		@struct,
        
		/// <remarks/>
		coll,
        
		/// <remarks/>
		tree,
	}
    
	/// <remarks/>
	public class ModeTypeRestrict 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public ModeTypeRestrictType Type;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowRun;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowRunSpecified;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowCreate;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowCreateSpecified;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowUpdate;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowUpdateSpecified;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowDelete;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowDeleteSpecified;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowRead;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowReadSpecified;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowWrite;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowWriteSpecified;
	}
    
	/// <remarks/>
	public enum ModeTypeRestrictType 
	{
        
		/// <remarks/>
		action,
        
		/// <remarks/>
		section,
        
		/// <remarks/>
		field,
	}
    
	/// <remarks/>
	public class ModeType 
	{
        
		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("LocalizedString", IsNullable=false)]
		public ArrayList Name;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("Restrict")]
		public ArrayList Restrict;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string ID;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public bool AllowAllActions;
        
		/// <remarks/>
		[System.Xml.Serialization.XmlIgnoreAttribute()]
		public bool AllowAllActionsSpecified;
	}

	
}
