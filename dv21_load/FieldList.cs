using dv21_util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Schema;



namespace dv21
{
    public class  FieldList
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

        public FieldList()
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
                    return "text";

                case "string":
                    return "varchar";

                case "unistring":
                    return "varchar";

                case "fileid":
                    return "bytea";

                case "float":
                    return "numeric(18,8)";

                case "double":
                    return "numeric(18,8)";

                case "text":
                    return "text";

                case "image":
                    return "bytea";

                case "json":
                    return "jsonb";

            }
            return "integer";

        }

        private void MakeSectionType(dv21.SectionType s, dv21.SectionType s_parent)
        {

            StringBuilder cc = new StringBuilder();

            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','','','','','" + s.Name[0].Value + "'");

            int i;
            if (s.Field != null)
            {
                for (i = 0; i < s.Field.Length; i++)
                {
                    string pgtype = MapBaseType(s.Field[i].Type.ToString());
                    //cc.AppendLine("COMMENT ON COLUMN " + CurrentSchema + s.Alias.ToLower() + "." + s.Field[i].Alias.ToLower() + " IS '" + s.Field[i].Name[0].Value + "';");


                    if (s.Field[i].Enum != null && s.Field[i].Enum.Length > 0)
                    {
                        //enums.AppendLine("CREATE TYPE " + CurrentSchema + s.Field[i].Alias.ToLower() + "_enum as ENUM (");

                        //int f;
                        //for (f = 0; f < s.Field[i].Enum.Length; f++)
                        //{
                        //    if (f > 0) enums.Append(",");
                        //    enums.AppendLine("'" + s.Field[i].Enum[f].Name + "'");
                        //}
                        //enums.AppendLine(");");


                        if (s.Field[i].NotNull)
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','"  + s.Field[i].Alias.ToLower() + "','"  + s.Field[i].Alias.ToLower() + "_enum','','not null','" + s.Field[i].Name[0].Value +"'" );
                        else
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" +  s.Field[i].Alias.ToLower() + "_enum','','null','" + s.Field[i].Name[0].Value +"'");

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
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','reference','" + refSection.Alias.ToLower() + "','not null','" + s.Field[i].Name[0].Value + "'");
                            else
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','reference','" + refSection.Alias.ToLower() + "','null','" + s.Field[i].Name[0].Value + "'");
                        }
                        else
                        {
                            // не удалось разрезолвить - значит просто поле
                            if (s.Field[i].NotNull)
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" + pgtype + "','','not null','" + s.Field[i].Name[0].Value + "'");
                            else

                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" + pgtype + "','','null','" + s.Field[i].Name[0].Value + "'");
                        }

                    }
                    else if (s.Field[i].MaxSpecified)
                    {
                        if (s.Field[i].NotNull)
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" +  s.Field[i].Alias.ToLower() + "','" + pgtype + "(" + s.Field[i].Max.ToString() + "),'','not null,'" + s.Field[i].Name[0].Value + "'");
                        else
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" +  s.Field[i].Alias.ToLower() + "','" + pgtype + "(" + s.Field[i].Max.ToString() + ")','','null','" + s.Field[i].Name[0].Value + "'");
                    }
                    else
                    {
                        if (s.Field[i].NotNull)
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" + pgtype + "','','not null','" + s.Field[i].Name[0].Value + "'");
                        else

                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" + pgtype + "','','null','" + s.Field[i].Name[0].Value + "'");
                    }
                }
                

            }


            if (s.Type == dv21.SectionTypeType.tree)
            {
                
               sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Alias.ToLower() + "_parent','integer','" + s.Alias.ToLower() + "','null','Организация древовидной подчиненности'");
            }



           

            if (s.Section != null && s.Section.Length > 0)
            {

                for (i = 0; i < s.Section.Length; i++)
                {
                    MakeSectionType(s.Section[i], s);

                }
            }
       



            //sb.AppendLine("");




        }

        private string CurrentSchema;


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
            }




            for (i = 0; i < cd.Sections.Length; i++)
            {
                MakeSectionType(cd.Sections[i], null);
            }

            


        }



        public string Generate()
        {

            int i;
            if (cd.Schema == "")
            {
                CurrentSchema = "";
            }
            else
            {
                CurrentSchema = cd.Schema.ToLower();
            }




            GenerateOne();

           

            if (sb.ToString() != "") { 
                result.AppendLine("'Схема','Таблица','Поле','Тип','Ссылка на','Обязательность','Комментарий'");
                result.AppendLine(sb.ToString());
            }

            if (fk.ToString() != "")
            {
                result.AppendLine(fk.ToString());
            }
                

            return result.ToString().Replace("'","\"");
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


            if (sb.ToString() != "")
            {
                result.AppendLine("'Схема','Таблица','Поле','Тип','Ссылка на','Обязательность','Комментарий'");
                result.AppendLine(sb.ToString());
            }

            if (fk.ToString() != "")
            {
                result.AppendLine(fk.ToString());
            }


            return result.ToString().Replace("'", "\"");

        }





    }
}
