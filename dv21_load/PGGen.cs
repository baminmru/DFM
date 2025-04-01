using dv21_util;
using dv21_xsd;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Schema;

namespace dv21
{
    public class PGGen
    {

        private StringBuilder sb;
        private StringBuilder fk;
        private StringBuilder enums;
        private StringBuilder result;

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
            fk = new StringBuilder();
            enums = new StringBuilder();
            result = new StringBuilder();


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

                case "text":
                    return "text";

                case "unistring":
                    return "varchar";

                case "fileid":
                    return "bytea";

                case "image":
                    return "bytea";

                case "json":
                    return "jsonb";

                case "float":
                    return "numeric(18,8)";

                case "double":
                    return "numeric(18,8)";
             

            }
            return "integer";
        
	}

        private void  MakeSectionType(dv21.SectionType s, dv21.SectionType s_parent)
        {


            string idType = MapBaseType(MyUtils.GetIDType(s));
            string idName = MyUtils.GetIDName(s);
            string idTypeRef;
            string idNameRef;

            FieldType idFld = MyUtils.GetIDField(s);

            StringBuilder cc = new StringBuilder();

            sb.AppendLine(" -- start " + s.Alias.ToLower());

            cc.AppendLine("COMMENT ON TABLE " + CurrentSchema  + s.Alias.ToLower() + " IS '" + s.Name[0].Value + "';");

            sb.AppendLine("CREATE TABLE IF NOT EXISTS  " + CurrentSchema  + s.Alias.ToLower() + "(");

           
           
            sb.AppendLine("\t\t" + idName + " "+ idType + " PRIMARY KEY");
            cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + idName + " IS '" + s.Name[0].Value + " ключ';");
           




            if (s_parent != null)
            {

                idTypeRef = MapBaseType(MyUtils.GetIDType(s_parent));
                idNameRef = MyUtils.GetIDName(s_parent);

                sb.AppendLine("\t\t," + s_parent.Alias.ToLower() + "_" +idNameRef+ " " + idTypeRef + " not null");

                fk.AppendLine("ALTER TABLE " + CurrentSchema + s.Alias.ToLower() + " ADD CONSTRAINT "  + s.Alias.ToLower() + "_" + s_parent.Alias.ToLower() + "_" + idNameRef + "_fk FOREIGN KEY(" + s_parent.Alias.ToLower() + "_" + idNameRef + ") REFERENCES " + CurrentSchema + s_parent.Alias.ToLower() + " (" +idNameRef+ ");");

                cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + s_parent.Alias.ToLower() + "_" + idNameRef + " IS ' ссылка на родительскую таблицу " + s_parent.Name[0].Value + "';");
            }

            int i;
            if (s.Field != null) {
                for (i = 0; i < s.Field.Length; i++)
                {

                    if (s.Field[i].Alias != idName)
                    {

                        string pgtype = MapBaseType(s.Field[i].Type.ToString());
                        cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + s.Field[i].Alias.ToLower() + " IS '" + s.Field[i].Name[0].Value + "';");


                        if (s.Field[i].Type ==FieldTypeType.@enum && s.Field[i].Enum.Length > 0)
                        {
                            enums.AppendLine("CREATE TYPE " +  s.Field[i].EnumName + " as ENUM (");

                            int f;
                            for (f = 0; f < s.Field[i].Enum.Length; f++)
                            {
                                if (f > 0) enums.Append(",");
                                enums.AppendLine("'" + s.Field[i].Enum[f].Name + "'");
                            }
                            enums.AppendLine(");");


                            if (s.Field[i].NotNull)
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " +  s.Field[i].EnumName + " not null");
                            else
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " +  s.Field[i].EnumName );

                        }
                        else if (s.Field[i].Reference)
                        {
                            CardDefinition refType = null;
                            SectionType refSection = null;
                            string refSchema = "";



                            refType = MyUtils.GetReferencedType(cd, s.Field[i].RefType);
                            if (refType != null)
                                refSection = MyUtils.GetReferencedSection(refType.Sections, s.Field[i].RefSection);

                            if (refSection != null)
                            {

                                idTypeRef = MapBaseType(MyUtils.GetIDType(refSection));
                                idNameRef = MyUtils.GetIDName(refSection);


                                // тип колонки приведем к типу ключа таблицы на которую ссылаемся
                                refSchema = refType.Schema.ToLower() + ".";
                                if (s.Field[i].NotNull)
                                    sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + idTypeRef + " not null");
                                else
                                    sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + idTypeRef);

                                fk.AppendLine("ALTER TABLE " + CurrentSchema + s.Alias.ToLower() + " ADD CONSTRAINT "  + s.Alias.ToLower() + "_" + s.Field[i].Alias.ToLower() + "_fk FOREIGN KEY(" + s.Field[i].Alias.ToLower() + ") REFERENCES " + refSchema + refSection.Alias.ToLower() + " (" + idNameRef + ");");
                            }
                            else
                            {
                                // не удалось разрезолвить - значит просто поле
                                if (s.Field[i].NotNull)
                                    sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + " not null ");
                                else
                                    sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "");
                            }

                        }
                        else if (s.Field[i].MaxSpecified)
                        {
                            if (s.Field[i].NotNull)
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "(" + s.Field[i].Max.ToString() + ") not null");
                            else
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "(" + s.Field[i].Max.ToString() + ")");
                        }
                        else
                        {
                            if (s.Field[i].NotNull)
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + " not null ");
                            else
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "");
                        }
                    }
                }

            }
                

            if (s.Type == dv21.SectionTypeType.tree)
            {
                sb.AppendLine("\t\t," + s.Alias.ToLower() + "_parent_id " + idType + " null "); 
                fk.AppendLine("ALTER TABLE " + CurrentSchema + s.Alias.ToLower() + " ADD CONSTRAINT "  + s.Alias.ToLower() + "_parent_id_fk FOREIGN KEY(" + s.Alias.ToLower() + "_parent_id) REFERENCES " + CurrentSchema + s.Alias.ToLower() + " (" + idName +");");
                cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + s.Alias.ToLower() + "_parent_id IS ' ссылка родителя в дереве ';");
            }



            if (s.AddHistory)
            {
                sb.AppendLine("\t\t,effective_date_start date");
                sb.AppendLine("\t\t,effective_date_stop date");
            }

            if (s.AddWhoInfo)
            {
                sb.AppendLine("\t\t,created_ts timestamp");
                sb.AppendLine("\t\t,created_by text");
                sb.AppendLine("\t\t,modified_ts timestamp");
                sb.AppendLine("\t\t,modified_by text");
            }


                sb.AppendLine(");"); // end of create table

                sb.AppendLine(" -- comments for " + s.Alias.ToLower());
                sb.AppendLine(cc.ToString());
                sb.AppendLine(" -- end " + s.Alias.ToLower());

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

            
            
            sb.AppendLine("");




        }

        private string CurrentSchema;

        public string Generate()
        {

            GenerateOne();

            result.AppendLine(enums.ToString());
            result.AppendLine(sb.ToString());
            result.AppendLine(fk.ToString());

            return   result.ToString();
        }


        public void GenerateOne()
        {

            int i;
            if (cd.Schema == "")
            {
                CurrentSchema = "";
            }
            else
            {
                CurrentSchema = cd.Schema.ToLower();
                result.AppendLine("CREATE SCHEMA IF NOT EXISTS " + CurrentSchema + ";");
                CurrentSchema = CurrentSchema + ".";

            }

            for (i = 0; i < cd.Sections.Length; i++)
            {
                MakeSectionType(cd.Sections[i], null);
            }

            
        }


        public string GenerateAll()
        {



            CardDefinition refCD;

            int i;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {
                refCD = null;
                try
                {
                    refCD = MyUtils.cards[i];
                }
                catch { }
                if (refCD != null)
                {
                    cd = refCD;
                    GenerateOne();

                }
            }


            

            result.AppendLine(enums.ToString());
            result.AppendLine(sb.ToString());
            result.AppendLine(fk.ToString());

            return result.ToString();

        }



    }




}
