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

                case "float":
                    return "numeric(18,8)";

                case "double":
                    return "numeric(18,8)";
             

            }
            return "integer";
        
	}

        private void  MakeSectionType(dv21.SectionType s, dv21.SectionType s_parent)
        {

            StringBuilder cc = new StringBuilder();

            sb.AppendLine(" -- start " + s.Alias.ToLower());

            cc.AppendLine("COMMENT ON TABLE " + CurrentSchema  + s.Alias.ToLower() + " IS '" + s.Name[0].Value + "';");

            sb.AppendLine("CREATE TABLE IF NOT EXISTS  " + CurrentSchema  + s.Alias.ToLower() + "(");
            sb.AppendLine("\t\tid integer PRIMARY KEY");

            cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema  + s.Alias.ToLower() + ".id IS '" + s.Name[0].Value + " первичный ключ';");


            if (s_parent != null)
            {
                sb.AppendLine("\t\t," + s_parent.Alias.ToLower() + "id integer not null");

                fk.AppendLine("ALTER TABLE " + CurrentSchema + s.Alias.ToLower() + " ADD CONSTRAINT " + "fk_" + s.Alias.ToLower() + "_" + s_parent.Alias.ToLower() + " FOREIGN KEY(" + s_parent.Alias.ToLower() + "id) REFERENCES " + CurrentSchema + s_parent.Alias.ToLower() + " (id);");

                cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema  + s.Alias.ToLower() + "."+ s_parent.Alias.ToLower()  + "id IS ' ссылка на родительскую таблицу " + s_parent.Name[0].Value + "';");
            }

            int i;
            if (s.Field != null) {
                for (i = 0; i < s.Field.Length; i++)
                {
                    string pgtype = MapBaseType(s.Field[i].Type.ToString());
                    cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + s.Field[i].Alias.ToLower() + " IS '" + s.Field[i].Name[0].Value + "';");


                    if (s.Field[i].Enum != null && s.Field[i].Enum.Length > 0)
                    {
                        enums.AppendLine("CREATE TYPE " + CurrentSchema + s.Field[i].Alias.ToLower() + "_enum as ENUM (");

                        int f;
                        for (f = 0; f < s.Field[i].Enum.Length; f++)
                        {
                            if (f > 0) enums.Append(",");
                            enums.AppendLine("'" + s.Field[i].Enum[f].Name + "'");
                        }
                        enums.AppendLine(");");


                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + CurrentSchema + s.Field[i].Alias.ToLower() + "_enum not null");
                        else
                            sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + CurrentSchema + s.Field[i].Alias.ToLower() + "_enum");

                    }
                    else if (s.Field[i].ReferenceSpecified)
                    {
                        CardDefinition refType = null;
                        SectionType refSection = null;
                        string refSchema = "";

                        refType = MyUtils.GetReferencedType(cd, s.Field[i].RefType);
                        if (refType != null)
                            refSection = MyUtils.GetReferencedSection(refType.Sections, s.Field[i].RefSection);

                        if (refSection != null)
                        {
                            refSchema = refType.Schema.ToLower() + ".";
                            if (s.Field[i].NotNull)
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + " not null");
                            else
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype );

                            fk.AppendLine("ALTER TABLE " + CurrentSchema + s.Alias.ToLower() + " ADD CONSTRAINT " + "fk_" + s.Alias.ToLower() + "_" + s.Field[i].Alias.ToLower() + " FOREIGN KEY(" + s.Field[i].Alias.ToLower() + ") REFERENCES " + refSchema + refSection.Alias.ToLower() + " (id);");
                        }
                        else
                        {
                            // не удалось разрезолвить - значит просто поле
                            if (s.Field[i].NotNull)
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + " not null ");
                            else
                                sb.AppendLine("\t\t," + s.Field[i].Alias.ToLower() + " " + pgtype + "");
                        }

                    } else if (s.Field[i].MaxSpecified)
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
                

            if (s.Type == dv21.SectionTypeType.tree)
            {
                sb.AppendLine("\t\t," + s.Alias.ToLower() + "_parent int null references  " + CurrentSchema + s.Alias.ToLower() +"( id )" );
                fk.AppendLine("ALTER TABLE " + CurrentSchema + s.Alias.ToLower() + " ADD CONSTRAINT " + "fk_" + s.Alias.ToLower() + "_tree FOREIGN KEY(" + s.Alias.ToLower() + "_parent) REFERENCES " + CurrentSchema + s.Alias.ToLower() + " (id);");
                cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + s.Alias.ToLower() + "_parent IS ' ссылка родителя в дереве ';");
            }



            if (s.AddHistory)
            {
                sb.AppendLine("\t\t,effective_date_start date");
                sb.AppendLine("\t\t,effective_date_end date");
            }

            if (s.AddWhoInfo)
            {
                sb.AppendLine("\t\t,created_at date");
                sb.AppendLine("\t\t,created_by text");
                sb.AppendLine("\t\t,updated_at date");
                sb.AppendLine("\t\t,updated_by text");
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
