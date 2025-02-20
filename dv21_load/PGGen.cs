using dv21_xsd;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Schema;

namespace dv21
{
    public class PGGen
    {

        private StringBuilder sb;
        private dv21.CardDefinition mcd;
        public dv21.CardDefinition cd
        {
            get
            {
                return mcd;
            }
            set
            {
                mcd = value;
            }
        }

        public PGGen()
        {
            mcd = null;
            sb = new StringBuilder();
        }


        private string MapBaseType(string dv21Type)
        {
            switch (dv21Type)
            {
                case "int":
                    return "integer";

                case "bool":
                    return "boolean";

                case "datetime":
                    return "date";

                case "enum":
                    return "integer";

                case "bitmask":
                    return "bytea";

                case "uniqueid":
                    return "UUID";

                case "userid":
                    return "varchar (64)";

                case "string":
                    return "varchar";

                case "unistring":
                    return "varchar";

                case "fileid":
                    return "bytea";

                case "float":
                    return "numeric(18,8)";

            }
            return "integer";
        
	}

        private void  MakeSectionType(dv21.SectionType s, dv21.SectionType s_parent)
        {

            StringBuilder cc = new StringBuilder();

            sb.AppendLine(" -- start " + s.Alias.ToLower());

            cc.AppendLine("COMMENT ON TABLE " + CurrentSchema + "." + s.Alias.ToLower() + " IS '" + s.Name[0].Value + "';");

            sb.AppendLine("CREATE TABLE IF NOT EXISTS  " + CurrentSchema + "." + s.Alias.ToLower() + "(");
            sb.AppendLine("\t\tid integer PRIMARY KEY");

            cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + "." + s.Alias.ToLower() + ".id IS '" + s.Name[0].Value + " первичный ключ';");


            if (s_parent != null)
            {
                sb.AppendLine("\t\t," + s_parent.Alias.ToLower()  + "id integer not null");
            }

            //try
            //{
                int i;
                for (i = 0; i < s.Field.Length; i++)
                {
                    string pgtype = MapBaseType(s.Field[i].Type.ToString());
                    cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + "."  +s.Alias.ToLower() + "."+ s.Field[i].Alias.ToLower() + " IS '" + s.Field[i].Name[0].Value + "';");

                if (!s.Field[i].MaxSpecified && ((s.Field[i].Enum == null) || (s.Field[i].Enum.Length == 0)))
                    {
                       
                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype +" not null");
                        else
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "");
                    }
                    else
                    {
                    if (s.Field[i].MaxSpecified)
                    {
                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "(" + s.Field[i].Max.ToString() + ") not null");
                        else
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "(" + s.Field[i].Max.ToString() + ")");
                    }
                    else
                    {
                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + " not null");
                        else
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "");

                    }
                        /*
                        if (s.Field[i].Enum != null && s.Field[i].Enum.Length > 0)
                        {
                            a = XSDT.NewAttribute(s.Field[i].Alias.ToLower(), XmlSchemaUse.Required, s.Field[i].Name[0].Value);
                            XmlSchemaSimpleType EnumType = new XmlSchemaSimpleType();
                            XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
                            restriction.BaseTypeName = MapBaseType(s.Field[i].Type.ToString());

                            int f;
                            for (f = 0; f < s.Field[i].Enum.Length; f++)
                            {
                                restriction.Facets.Add(XSDT.NewEnum(s.Field[i].Enum[f].Name, s.Field[i].Enum[f].Name));
                            }
                            EnumType.Content = restriction;
                            a.SchemaType = EnumType;
                        }
                        else //s.Field[i].MaxSpecified
                        {
                            a = XSDT.NewAttribute(s.Field[i].Alias.ToLower(), XmlSchemaUse.Required, s.Field[i].Name[0].Value);
                            XmlSchemaSimpleType SizeType = new XmlSchemaSimpleType();
                            XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
                            restriction.BaseTypeName = MapBaseType(s.Field[i].Type.ToString());
                            restriction.Facets.Add(XSDT.NewMaxLength(s.Field[i].Max, ""));
                            SizeType.Content = restriction;
                            a.SchemaType = SizeType;
                        }
                        */
                    }
                    
                }

                if (s.Type == dv21.SectionTypeType.tree)
                {
                    sb.AppendLine("\t\t," + s.Alias.ToLower() + "_parent int ");
                }



                if (s.AddHistory)
                {
                    sb.AppendLine("\t\t,effective_date_start date");
                    sb.AppendLine("\t\t,effective_date_end date");
                }

                if (s.AddWhoInfo)
                {
                    sb.AppendLine("\t\t,created_at date");
                    sb.AppendLine("\t\t,created_by varchar(64)");
                    sb.AppendLine("\t\t,updated_at date");
                    sb.AppendLine("\t\t,updated_by varchar(64)");
                }


                sb.AppendLine(");"); // end of create table


                if (s.Section != null && s.Section.Length > 0 )
                {

                    for (i = 0; i < s.Section.Length; i++)
                    {
                        MakeSectionType(s.Section[i], s);
                        
                    }
                }
            //}
            //catch
            //{
            //}

            sb.AppendLine(" -- comments for " +s.Alias.ToLower());
            sb.AppendLine(cc.ToString());
            sb.AppendLine(" -- end " + s.Alias.ToLower());
            sb.AppendLine("");




        }

        private string CurrentSchema;

        public string Generate()
        {

            int i;
            CurrentSchema = cd.Alias.ToLower().ToLower();

            sb.AppendLine("CREATE SCHEMA IF NOT EXISTS " + CurrentSchema +";");


            for (i = 0; i < cd.Sections.Length; i++)
            {
                MakeSectionType(cd.Sections[i], null);
            }

            return sb.ToString();
        }


    }
}
