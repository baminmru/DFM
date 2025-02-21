using dv21;
using dv21_util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace dv21
{
    public class JDLGen
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

        public JDLGen()
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
                    return "Integer";

                case "bool":
                    return "Boolean";

                case "datetime":
                    return "LocalDate";

                case "enum":
                    return "Enum";

                case "bitmask":
                    return "AnyBlob";

                case "uniqueid":
                    return "UUID";

                case "userid":
                    return "Integer";

                case "string":
                    return "String";

                case "unistring":
                    return "String";

                case "fileid":
                    return "ImageBlob";

                case "float":
                    return "Float";

                case "double":
                    return "Double";

            }
            return "integer";

        }

        private void MakeSectionType(dv21.SectionType s, dv21.SectionType s_parent)
        {


            sb.AppendLine(" // " + MyUtils.C1(s.Alias));

            sb.AppendLine(" /* " + s.Name[0].Value + " */");

            sb.AppendLine("entity  " + MyUtils.C1(s.Alias) + "{");
            


            if (s_parent != null)
            {
                sb.AppendLine("/* " + MyUtils.C1(s_parent.Alias) + "id - ' ссылка на родительскую таблицу " + s_parent.Name[0].Value + "' */");
                sb.AppendLine("\t\t" + MyUtils.C1(s_parent.Alias) + "Id Integer required ");


                fk.AppendLine("relationship OneToMany {");
                fk.AppendLine(MyUtils.C1(s.Alias) + " { " + MyUtils.C1(s_parent.Alias) + "Id} to " + MyUtils.C1(s_parent.Alias));
                fk.AppendLine("}");



            }

            int i;
            if (s.Field != null)
            {
                for (i = 0; i < s.Field.Length; i++)
                {
                    string pgtype = MapBaseType(s.Field[i].Type.ToString());
                    sb.AppendLine("/*" +  MyUtils.C1(s.Field[i].Alias) + " - '" + s.Field[i].Name[0].Value + "' */");


                    if (s.Field[i].Enum != null && s.Field[i].Enum.Length > 0)
                    {
                        enums.AppendLine("enum " + MyUtils.C1(s.Field[i].Alias) + "_enum {");

                        int f;
                        for (f = 0; f < s.Field[i].Enum.Length; f++)
                        {
                        //    if (f > 0) enums.Append(",");
                            enums.AppendLine( s.Field[i].Enum[f].Name );
                        }
                        enums.AppendLine("}");


                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + MyUtils.C1(s.Field[i].Alias) + "_enum required");
                        else
                            sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + MyUtils.C1(s.Field[i].Alias) + "_enum");

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
                                sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + " required");
                            else
                                sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype );

                            fk.AppendLine("relationship OneToMany {");
                            fk.AppendLine(MyUtils.C1(s.Alias) +" { " +  MyUtils.C1(s.Field[i].Alias) +"} to " + MyUtils.C1(refSection.Alias));
                            fk.AppendLine("}");
                        }
                        else
                        {
                            // не удалось разрезолвить - значит просто поле
                            if (s.Field[i].NotNull)
                                sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + " required ");
                            else
                                sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + "");
                        }

                    }
                    else if (s.Field[i].MaxSpecified)
                    {
                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + " maxlength(" + s.Field[i].Max.ToString() + ") required");
                        else
                            sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + " maxlength(" + s.Field[i].Max.ToString() + ")");
                    }
                    else
                    {
                        if (s.Field[i].NotNull)
                            sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + " required ");
                        else
                            sb.AppendLine("\t\t" + MyUtils.C1(s.Field[i].Alias) + " " + pgtype + "");
                    }
                }

            }


            if (s.Type == dv21.SectionTypeType.tree)
            {
                sb.AppendLine("\t\t" + MyUtils.C1(s.Alias) + "_parent Integer");
            }



            if (s.AddHistory)
            {
                sb.AppendLine("\t\t"+ MyUtils.C1("Effective_date_start") +" LocalDate");
                sb.AppendLine("\t\t"+ MyUtils.C1("Effective_date_end") +  " LocalDate");
            }

            if (s.AddWhoInfo)
            {
                sb.AppendLine("\t\t"+ MyUtils.C1("Created_at") + " LocalDate");
                sb.AppendLine("\t\t"+ MyUtils.C1("Created_by") + " String maxlength(64)");
                sb.AppendLine("\t\t"+ MyUtils.C1("Updated_at") + " LocalDate");
                sb.AppendLine("\t\t" + MyUtils.C1("Updated_by ") +" String maxlength(64)");
            }


            sb.AppendLine("}"); // end of create table

            
            if (s.Section != null && s.Section.Length > 0)
            {

                for (i = 0; i < s.Section.Length; i++)
                {
                    MakeSectionType(s.Section[i], s);

                }
            }
            



            sb.AppendLine("");




        }

       
        public string Generate()
        {

            int i;


            result.AppendLine("application {");
            result.AppendLine("  config {");
            result.AppendLine("     baseName " + MyUtils.C1(cd.Alias));
            result.AppendLine("     applicationType monolitic");
            result.AppendLine("	    authenticationType jwt");
            result.AppendLine("     serverPort 8080");
            result.AppendLine("	    buildTool 	maven");
            result.AppendLine("	    databaseType 	sql");
            result.AppendLine("	    prodDatabaseType   postgresql");
            result.AppendLine("	    devDatabaseType 	postgresql");
            result.AppendLine("	    clientFramework 	angularX");
            result.AppendLine("	    serviceDiscoveryType eureka");
            result.AppendLine("	    languages [ru,en]");
            result.AppendLine("	    packageName com.bami." + cd.Schema.ToLower() + "." + cd.Alias.ToLower());
            result.AppendLine("  }");
            result.AppendLine("  entities * ");
            result.AppendLine("}");





            for (i = 0; i < cd.Sections.Length; i++)
            {
                MakeSectionType(cd.Sections[i], null);
            }

            result.AppendLine(enums.ToString());
            result.AppendLine(sb.ToString());
            result.AppendLine(fk.ToString());

            return result.ToString();
        }











    }
}
