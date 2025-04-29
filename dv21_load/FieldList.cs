using dv21_util;
using System;
using System.Collections.Generic;
using System.Diagnostics.Eventing.Reader;
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

                case "int8":
                    return "int8";

                case "bool":
                    return "boolean";

                case "datetime":
                    return "date";

                case "timestamp":
                    return "timestamp";

                case "timestamptz":
                    return "timestamptz";

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

            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','','','','','" + s.Name[0].Value + "','" + MyUtils.CatCR(s.Documentation) + "'");


            string idType = MapBaseType(MyUtils.GetIDType(s));
            string idName = MyUtils.GetIDName(s);

            if(idName == "id")
            {
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + idName + "','" + idType + "','','key, not null','" + s.Name[0].Value +", ключ',''");
            }


            if (s_parent != null)
            {
                string pidType = MapBaseType(MyUtils.GetIDType(s_parent));
                string pidName = MyUtils.GetIDName(s_parent);

                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s_parent.Alias.ToLower() +"_" + pidName + "','" + pidType + "','','not null','ссылка на родительскую таблицу',''");
            }


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
                        {
                            if (s.Field[i].UseforPK)
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" +
                                    s.Field[i].EnumName + "','','key, not null','" + s.Field[i].Name[0].Value + ", ключ','" + MyUtils.CatCR( s.Field[i].Documentation) + "'");
                            else
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" +
                                                s.Field[i].EnumName + "','','not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                        }
                        else
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','" +
                                s.Field[i].EnumName + "','','null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");

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

                            string refIDType;
                            refIDType = MyUtils.GetIDType(refSection);

                            refSchema = refType.Schema.ToLower() + ".";
                            if (s.Field[i].NotNull)
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','reference "+ refIDType + "','" 
                                    + "(" +refSection.Alias.ToLower() +") " + refSection.Name[0].Value + "','not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                            else
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','reference " + refIDType + "','"
                                    + "(" + refSection.Alias.ToLower() + ") " + refSection.Name[0].Value + "','null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                        }
                        else
                        {
                            // не удалось разрезолвить - значит просто поле
                            if (s.Field[i].NotNull)
                            {
                                if (s.Field[i].UseforPK)
                                    sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                    + pgtype + "','','key, not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) +  "'");
                                else
                                    sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                    + pgtype + "','','not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) +  "'");
                            }
                            else

                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                    + pgtype + "','','null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                        }

                    }
                    else if (s.Field[i].MaxSpecified)
                    {
                        if (s.Field[i].NotNull)
                        {
                            if (s.Field[i].UseforPK)
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                + pgtype + "(" + s.Field[i].Max.ToString() + ")','','key, not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                            else
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                + pgtype + "(" + s.Field[i].Max.ToString() + ")','','not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                        }
                        else
                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                + pgtype + "(" + s.Field[i].Max.ToString() + ")','','null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                    }
                    else
                    {
                        if (s.Field[i].NotNull)
                        {
                            if (s.Field[i].UseforPK)
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                + pgtype + "','','key, not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                            else
                                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                + pgtype + "','','not null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                        }
                        else

                            sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Field[i].Alias.ToLower() + "','"
                                + pgtype + "','','null','" + s.Field[i].Name[0].Value + "','" + MyUtils.CatCR(s.Field[i].Documentation) + "'");
                    }
                }
                

            }


            if (s.Type == dv21.SectionTypeType.tree)
            {
                
               sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','" + s.Alias.ToLower() + "_parent','"+idType+"','" + s.Alias.ToLower() + "','null','Организация древовидной подчиненности',''");
            }




            if (s.AddHistory)
            {
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','is_effective','bool','','not null',Флаг актуальности записи',''");
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','effective_begin_date','date','','not null','Дата начала действия записи',''");
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','effective_end_date','date','','not null','Дата окончания действия записи',''");
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','process_id','int8','','null','Идентификатор процесса (workflow), создавшего новую версию записи'");
            }

            if (s.AddWhoInfo)
            {
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','created_ts','timestamptz','','not null','Когда создана запись',''");
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','created_by','text','','not null','Кем создана запись',''");
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','modified_ts','timestamptz','','not null','Когда изменена запись',''");
                sb.AppendLine("'" + CurrentSchema + "','" + s.Alias.ToLower() + "','modified_by','text','','null','Кем изменена запись',''");
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
                result.AppendLine("'Схема','Таблица','Поле','Тип','Ссылка на','Обязательность','Описание','Комментарий'");
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
                result.AppendLine("'Схема','Таблица','Поле','Тип','Ссылка на','Обязательность','Описание','Комментарий'");
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
