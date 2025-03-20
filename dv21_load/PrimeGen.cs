using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Net.Mime.MediaTypeNames;
using System.Windows.Forms;
using dv21_util;
using System.Runtime.CompilerServices;


namespace dv21_load
{
    public class PrimeGen
    {


        private void TypeMake_Component( dv21.CardDefinition ot, String OutputFolder) { 
            StringBuilder sb;
            sb = new StringBuilder();
            sb.AppendLine("<p-card   > ");
            sb.AppendLine("  <ng-template pTemplate=\"header\">");
            //sb.AppendLine(" <i class=\"fa " + ot.objIconCls + "\"" aria - hidden =\"True\"></i> %typename% ")
            sb.AppendLine(" <i  aria-hidden=\"True\"></i> %typename% ");
            sb.AppendLine("  </ng-template>");
            sb.AppendLine("	<p-tabView  [(activeIndex)]=\"activeIndex\">");

            dv21.SectionType P;
            Boolean isFirst;
            int i, j;

            isFirst =true;

            for (i = 0; i < ot.Sections.Length; i++) {

                P = ot.Sections[i];

                if (isFirst) {
                    isFirst = false;
                    sb.AppendLine("		<p-tabPanel  header=\"" + P.Name[0].Value + "\" > ");
                }
                else {
                    sb.AppendLine("		<p-tabPanel  header=\"" + P.Name[0].Value + "\" > ");
                }
                sb.AppendLine("			<app-" + MyUtils.DeCap(P.Alias) + "></app-" + MyUtils.DeCap(P.Alias) + ">");
                sb.AppendLine("		</p-tabPanel>");
                sb.AppendLine("		");
            }
            sb.AppendLine("	</p-tabView>");
            sb.AppendLine("</p-card>");

            String ss;
            ss = sb.ToString();
            ss = ss.Replace("%typename%", ot.Name[0].Value);
            ss = ss.Replace("%ns%", ot.Schema);
            ss = ss.Replace("%type%", ot.Alias);
    

            MyUtils.Tool_WriteFile(ss, OutputFolder + "\\ts\\" + ot.Alias +"\\" ,  ot.Alias + ".component.html", false);

            sb = new StringBuilder();
            sb.AppendLine("import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core'; ");
            sb.AppendLine("import { %type% } from \"./../%type%\"; ");
            sb.AppendLine("import { AppService } from \"./../app.service\"; ");
            sb.AppendLine(" ");
            sb.AppendLine("@Component({ ");
            sb.AppendLine("  selector: 'app-%type%', ");
            sb.AppendLine("  templateUrl: './%type%.component.html', ");
            sb.AppendLine("  styleUrls: ['./%type%.component.scss'] ");
            sb.AppendLine("}) ");
            sb.AppendLine("export class %type%Component implements OnInit { ");
            sb.AppendLine("  activeIndex:number=0;");
            sb.AppendLine("  constructor(public AppService:AppService) { } ");
            sb.AppendLine(" ");
            sb.AppendLine("  ngOnInit() { ");
            sb.AppendLine("  } ");
            sb.AppendLine(" ");
            sb.AppendLine("}");

            ss = sb.ToString();
            ss = ss.Replace("%typename%", ot.Name[0].Value);
            ss = ss.Replace("%ns%", ot.Schema);
            ss = ss.Replace("%type%", ot.Alias);

            MyUtils.Tool_WriteFile(ss, OutputFolder + "\\ts\\" + ot.Alias + "\\", ot.Alias + ".component.ts", false);

            MyUtils.Tool_WriteFile(" ", OutputFolder + "\\ts\\" + ot.Alias + "\\", ot.Alias + ".component.scss", false);

    }


            
        private string PartMake_Service(dv21.SectionType P, dv21.CardDefinition ot)
        {
            
            StringBuilder sb;
            sb = new StringBuilder();
            sb.AppendLine("import { Injectable } from '@angular/core';");
            sb.AppendLine("import { HttpClient, HttpRequest, HttpClientModule, HttpHeaders, HttpResponse } from '@angular/common/http';");
            sb.AppendLine("import { Observable } from 'rxjs';");
            sb.AppendLine("import { environment } from '../environments/environment';");
            sb.AppendLine("import { enums } from './enums';");
            sb.AppendLine("import { %type%} from './%type%';");

            sb.AppendLine("@Injectable()");
            sb.AppendLine("export class %obj%_Service {");
            sb.AppendLine("	private serviceURL: string = environment.baseAppUrl;");
            sb.AppendLine(" ");
            sb.AppendLine("	//Create constructor to get Http instance");
            sb.AppendLine("	constructor(private http:HttpClient) { ");
            sb.AppendLine("	}");
            sb.AppendLine("	");
            sb.AppendLine("	");



            dv21.FieldType fld;
            dv21.FieldTypeType ft;
            int i;

            var loopTo = P.Field.Length;
            for (i = 0; i < loopTo; i++)
            {
                fld = P.Field[i];
                ft = fld.Type;
                
                sb.AppendLine("\t" + fld.Alias + ":string = '';");
                
            }

            sb.AppendLine("	PageSize:number=10;");
            sb.AppendLine("	PageUrl:string='';");
            sb.AppendLine("    ");
            sb.AppendLine("	//Fetch all %obj%s");
            sb.AppendLine("    getAll_%obj%s(): Observable<%type%.%obj%[]> {");
            sb.AppendLine("		var qry:string;");
            sb.AppendLine("		qry='';");
            sb.AppendLine("		");

            loopTo = P.Field.Length;
            for (i = 0; i < loopTo; i++)
            {
                fld = P.Field[i];
                ft = fld.Type;
                
                sb.AppendLine("		if(this." + fld.Alias + "!=''){");
                sb.AppendLine("			if(qry !='') qry=qry +'&';");
                sb.AppendLine("			qry='" + fld.Alias + "='+encodeURIComponent(this." + fld.Alias + ")");
                sb.AppendLine("		}");
               
            }

            sb.AppendLine("		/*");
            sb.AppendLine("		if(this.PageNo!=null){");
            sb.AppendLine("			if(qry !='') qry=qry +" + ";");
            sb.AppendLine("			//qry='page='+this.PageNo;");
            sb.AppendLine("			qry='_getpagesoffset=' + ((this.PageNo-1) * this.PageSize)+'&_count=' +this.PageSize;");
            sb.AppendLine("		}");
            sb.AppendLine("		*/");
            sb.AppendLine("		");
            sb.AppendLine("		let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
            sb.AppendLine("		if(this.PageUrl!=''){");
            sb.AppendLine("			return this.http.get<%type%.%obj%[]>(this.PageUrl, { headers: cpHeaders })");
            sb.AppendLine("		}else{");
            sb.AppendLine("			if(qry !='')");
            sb.AppendLine("				qry='?' +qry;");
            sb.AppendLine("			return this.http.get<%type%.%obj%[]>(this.serviceURL + '/%obj%/view/'+qry, { headers: cpHeaders })");
            sb.AppendLine("        }");
            sb.AppendLine("    }");
            sb.AppendLine("	");
            sb.AppendLine("	clearSearch():void{");

            var loopTo2 = P.Field.Length;
            for (i = 0; i < loopTo2; i++)
            {
                fld = P.Field[i];
                ft = fld.Type;
                sb.AppendLine("\t" + "this." + fld.Alias + " = '';");
                
            }
            sb.AppendLine("		");
            sb.AppendLine("	}");




            sb.AppendLine(" ");
            sb.AppendLine("	   //Create %obj%");
            sb.AppendLine("    create_%obj%(%obj%: %type%.%obj%): Observable<%type%.%obj% > {");
            sb.AppendLine("       // %obj%.%obj%id = '';");
            sb.AppendLine("        let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
            sb.AppendLine("        return this.http.post<%type%.%obj% >(this.serviceURL + '/%obj%/', %obj%, { headers: cpHeaders })");
            sb.AppendLine("		");
            sb.AppendLine("    }");
            sb.AppendLine("	");




            bool isroot = false;

            if (Information.TypeName(P.Parent.Parent) == "OBJECTTYPE")
            {
                ot = P.Parent.Parent;
                isroot = true;
            }



            bool AddByParent;

            AddByParent = false;


            if (!isroot)
            {
                AddByParent = true;
            }
            else if (ot.IsSingleInstance == MTZMetaModel.MTZMetaModel.enumBoolean.Boolean_Net)
            {


                // все  разделы верхнего уровня  впихиваем в  0 -раздел
                if (P.Sequence != 0)
                {
                    AddByParent = true;
                }
            }



            if (AddByParent)
            {
                sb.AppendLine("	//Fetch %obj% by parent");
                sb.AppendLine("    get_%obj%ByParent(parentId: string): Observable<%type%.%obj%[]> {");
                sb.AppendLine("        let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
                sb.AppendLine("		   //console.log(this.serviceURL +'/%obj%/byparent/'+ parentId)");
                sb.AppendLine("        return this.http.get<%type%.%obj%[]>(this.serviceURL + '/%obj%/byparent/' + parentId, { headers: cpHeaders })//.catch(err => { console.log(err) return Observable.of(err) })");
                sb.AppendLine("    }	");
                sb.AppendLine("	");
            }

            sb.AppendLine("	//Fetch %obj% by id");
            sb.AppendLine("    get_%obj%ById(%obj%id: string): Observable<%type%.%obj%> {");
            sb.AppendLine("        let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
            sb.AppendLine("		//console.log(this.serviceURL +'/%obj%/'+ %obj%id)");
            sb.AppendLine("        return this.http.get<%type%.%obj%>(this.serviceURL + '/%obj%/' + %obj%id, { headers: cpHeaders })//.catch(err => { console.log(err) return Observable.of(err) })");
            sb.AppendLine("    }	");
            sb.AppendLine("	");
            sb.AppendLine("	   //Update %obj%");
            sb.AppendLine("    update_%obj%(%obj%: %type%.%obj%):Observable<Object > {");
            sb.AppendLine("        let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
            sb.AppendLine("        return this.http.put(this.serviceURL + '/%obj%/' + %obj%." + DeCap(P.Name) + idConst + ", %obj%, { headers: cpHeaders })");
            sb.AppendLine("    }");
            sb.AppendLine("	");
            sb.AppendLine("    //Delete %obj%	");
            sb.AppendLine("    delete_%obj%ById(%obj%id: string): Observable<Object> {");
            sb.AppendLine("        let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
            sb.AppendLine("        return this.http.delete(this.serviceURL + '/%obj%/' + %obj%id, { headers: cpHeaders })");
            sb.AppendLine("            ");
            sb.AppendLine("			");
            sb.AppendLine("    }	");
            sb.AppendLine("	");
            sb.AppendLine("	private mSelecetd:%type%.%obj%|undefined = undefined;");
            sb.AppendLine("	");
            sb.AppendLine("	public 	get Selected():%type%.%obj%|undefined{ return this.mSelecetd;}");
            sb.AppendLine("	");
            sb.AppendLine("	public  set Selected(_%obj%:%type%.%obj%|undefined){ this.mSelecetd=_%obj%; }");
            sb.AppendLine(" ");
            sb.AppendLine("}");

            string ss = sb.ToString();
            ss = ss.Replace("%obj%", DeCap(P.Name));
            ss = ss.Replace("%ns%", txtNS.Text);
            ss = ss.Replace("%type%", ot.Name);

            Tool_WriteFile(ss, textBoxOutPutFolder.Text + @"\ts\", P.Name + ".service.ts", false);
            return default;

        }




    }
}
