using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Windows.Forms;
using dv21_util;
using System.Collections.Generic;

namespace dv21
{

    public partial class frmWebAPI_PrimeNG
    {

       


        private string idConst = "Id";

        public frmWebAPI_PrimeNG()
        {
            InitializeComponent();
        }

        private void frmA4_Load(object sender, EventArgs e)
        {

           
        }

        private void button3_Click(object sender, EventArgs e)
        {
            folderBrowserDialogProjectOutput.SelectedPath = textBoxOutPutFolder.Text;
            folderBrowserDialogProjectOutput.ShowDialog();
            textBoxOutPutFolder.Text = folderBrowserDialogProjectOutput.SelectedPath;
            if (!textBoxOutPutFolder.Text.EndsWith(@"\"))
            {
                textBoxOutPutFolder.Text += @"\";
            }
        }

        private void cmdSelectAll_Click(object sender, EventArgs e)
        {
            //int i;
            //var loopTo = chkObjType.Items.Length - 1;
            //for (i = 0; i <= loopTo; i++)
            //    chkObjType.SetItemChecked(i, true);
        }

        private void cmdClearAll_Click(object sender, EventArgs e)
        {
            //int i;
            //var loopTo = chkObjType.Items.Length - 1;
            //for (i = 0; i <= loopTo; i++)
            //    chkObjType.SetItemChecked(i, false);
        }

        private void cmdGen_Click(object sender, EventArgs e)
        {
            int i;
            StringBuilder sw;

            String om;

            CardDefinition ot =null;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {
              
                ot = MyUtils.cards[i];
              
                if (ot != null)
                {
             

                 
                   idConst = "id";
                  


                    Text = "Generating stores";
                    Application.DoEvents();

                    Application.DoEvents();
                    sw = new StringBuilder();
                    dv21.CardDefinition pkg;
                   






                    // '''''''''''''''''' Make Model Types for TypeScript objects

                 
                    sw = new StringBuilder();

                        
                    sw.AppendLine("import { enums } from './enums';");

                    sw.Append("\r\n" + "export namespace " + ot.Alias + " { ");


                    sw.Append("\r\n" + "\t" + "/* " + ot.Alias + " -  " + ot.Name[0].Value + " */ ");

                    sw.Append(PartMake_TSList(ot, ot.Sections));
                    sw.Append("\r\n" + "}");
                    Tool_WriteFile(sw.ToString(), textBoxOutPutFolder.Text + @"\ts\", ot.Alias + ".ts", false);

                    Application.DoEvents();


                    // '''''''''''''''''' Make Services for TypeScript objects
                    sw = new StringBuilder();
                    PartMake_ServiceList(ot, ot.Sections);

                    Application.DoEvents();



                    // '''''''''''''''''' Make Components for TypeScript objects

                    
                        Application.DoEvents();
                        
                        sw = new StringBuilder();
                        

                        PartMake_Components(ot, ot.Sections);



                        // ''''''''''''''''' make type level component
                        TypeMake_Component(ot);

                        Application.DoEvents();
                    



                    AppMake_module();

                    //// ''''''''''  app.service 
                    //sw = new StringBuilder();

                    //sw.AppendLine("import { Injectable } from '@angular/core'; ");
                    //sw.AppendLine("import { HttpClient, HttpRequest, HttpClientModule, HttpHeaders, HttpResponse } from '@angular/common/http'; ");
                    //sw.AppendLine("import { Observable,BehaviorSubject } from 'rxjs'; ");
                    //sw.AppendLine("import { environment } from '../environments/environment';");
                    //sw.AppendLine("");
                    //var loopTo11 = chkObjType.CheckedItems.Length - 1;
                    //for (i = 0; i <= loopTo11; i++)
                    //{
                    //    ti = (tmpInst)chkObjType.CheckedItems[i];
                    //    ot = Module1.model.OBJECTTYPE.Item(ti.ID.ToString());
                    //    sw.AppendLine("import { %type% } from \"./../%type%\";".Replace("%type%", ot.Alias));
                    //    Application.DoEvents();
                    //}

                    //sw.AppendLine("	");
                    //sw.AppendLine("export class ComboInfo{ ");
                    //sw.AppendLine("	id:string; ");
                    //sw.AppendLine("	name:string; ");
                    //sw.AppendLine("} ");

                    //sw.AppendLine("export class enumInfo{ ");
                    //sw.AppendLine("	id:number; ");
                    //sw.AppendLine("	name:string; ");
                    //sw.AppendLine("} ");

                    //sw.AppendLine(" ");
                    //sw.AppendLine("@Injectable() ");
                    //sw.AppendLine("export class AppService { ");
                    //sw.AppendLine("	private serviceURL: string = environment.baseAppUrl; ");
                    //sw.AppendLine("	 ");
                    //sw.AppendLine("	 constructor(private http:HttpClient) {  ");
                    //sw.AppendLine("		this.RefreshCombo(); ");
                    //sw.AppendLine("	} ");
                    //sw.AppendLine("	");

                    //sw.AppendLine(" ");
                    //var loopTo12 = chkObjType.CheckedItems.Length - 1;
                    //for (i = 0; i <= loopTo12; i++)
                    //{
                    //    ti = (tmpInst)chkObjType.CheckedItems[i];
                    //    ot = Module1.model.OBJECTTYPE.Item(ti.ID.ToString());
                    //    sw.AppendLine(Make_AppServiceList(ot.Sections));
                    //    Application.DoEvents();
                    //}

                    //sw.AppendLine(" ");
                    //var loopTo13 = chkObjType.CheckedItems.Length - 1;
                    //for (i = 0; i <= loopTo13; i++)
                    //{
                    //    ti = (tmpInst)chkObjType.CheckedItems[i];
                    //    ot = Module1.model.OBJECTTYPE.Item(ti.ID.ToString());
                    //    sw.AppendLine(Make_AppComboSupport(ot.Sections));
                    //    Application.DoEvents();
                    //}

                    //sw.AppendLine(" ");
                    //sw.AppendLine("public RefreshCombo(){");
                    //var loopTo14 = chkObjType.CheckedItems.Length - 1;
                    //for (i = 0; i <= loopTo14; i++)
                    //{
                    //    ti = (tmpInst)chkObjType.CheckedItems[i];
                    //    ot = Module1.model.OBJECTTYPE.Item(ti.ID.ToString());
                    //    sw.AppendLine(Make_AppComboRefresh(ot.Sections));
                    //    Application.DoEvents();
                    //}
                    //sw.AppendLine("}");
                    //sw.AppendLine(" ");


                    //sw.AppendLine(" // enum support");

                    //var loopTo15 = (int)Module1.model.FieldTYPE.Length;
                    //for (i = 1; i <= loopTo15; i++)
                    //{
                    //    ft = Module1.model.FieldTYPE[i - 1];
                    //    if (ft.TypeStyle ==TypeStyle_Perecislenie)
                    //    {
                    //        sw.AppendLine("\r\n" + "\t" + "/* " + ft.Name + " - " + ft.the_Comment + " */ ");
                    //        sw.AppendLine("	public enum" + LATIR2Framework.FieldTypesHelper.MakeValidName(ft.Name) + "Combo(){");
                    //        sw.AppendLine("		return this.enum" + LATIR2Framework.FieldTypesHelper.MakeValidName(ft.Name) + ";");
                    //        sw.AppendLine("	}");
                    //        sw.AppendLine("	enum" + LATIR2Framework.FieldTypesHelper.MakeValidName(ft.Name) + ":Array<enumInfo> =[");

                    //        var loopTo16 = (int)ft.ENUMITEM.Length;
                    //        for (eidx = 1; eidx <= loopTo16; eidx++)
                    //        {
                    //            sw.Append("\r\n" + "\t");
                    //            if (eidx > 1)
                    //            {
                    //                sw.Append(",");
                    //            }
                    //            ei = ft.ENUMITEM.Item(eidx);
                    //            sw.Append(" {id:" + ei.NameValue + ",name:'" + ei.Name + "'}");
                    //        }
                    //        sw.AppendLine("	];");
                    //    }
                    //    Application.DoEvents();
                    //}

                    //sw.AppendLine(" ");




                    //// end class
                    //sw.AppendLine("}");

                    //Tool_WriteFile(sw.ToString(), textBoxOutPutFolder.Text + @"\ts\", "app.service.ts", false);

                }
            }




            MessageBox.Show("Done");


         

         
        }


      

     




        public void AppMake_module()
        {

            int i;
            int j;
          
            dv21.CardDefinition ot;
            dv21.SectionType P;
            bool isFirst;

            StringBuilder sb;
            sb = new StringBuilder();




            sb.AppendLine("import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; ");
            sb.AppendLine("import { BrowserModule } from '@angular/platform-browser'; ");
            sb.AppendLine("import { NgModule } from '@angular/core'; ");
            sb.AppendLine("import { FormsModule } from '@angular/forms'; ");
            sb.AppendLine("import { HttpClientModule, HttpClient } from '@angular/common/http'; ");


            // '''''''''''''''  primeng modules 
            sb.AppendLine(" //PRIMENG ");
            sb.AppendLine("import { AnimateModule } from 'primeng/animate';");
            sb.AppendLine("import { AvatarModule } from 'primeng/avatar';");
            sb.AppendLine("import { AccordionModule } from 'primeng/accordion';");
            sb.AppendLine("import { AutoCompleteModule } from 'primeng/autocomplete';");
            sb.AppendLine("import { BadgeModule } from 'primeng/badge';");
            sb.AppendLine("import { BreadcrumbModule } from 'primeng/breadcrumb';");
            sb.AppendLine("import { BlockUIModule } from 'primeng/blockui';");
            sb.AppendLine("import { ButtonModule } from 'primeng/button';");
            sb.AppendLine("import { CalendarModule } from 'primeng/calendar';");
            sb.AppendLine("import { CardModule} from 'primeng/card';");
            sb.AppendLine("import { CarouselModule } from 'primeng/carousel';");
            sb.AppendLine("import { CascadeSelectModule } from 'primeng/cascadeselect';");
            sb.AppendLine("import { ChartModule } from 'primeng/chart';");
            sb.AppendLine("import { CheckboxModule } from 'primeng/checkbox';");
            sb.AppendLine("import { ChipsModule } from 'primeng/chips';");
            sb.AppendLine("import { ChipModule } from 'primeng/chip';");
            sb.AppendLine("import { ColorPickerModule } from 'primeng/colorpicker';");
            sb.AppendLine("import { ConfirmDialogModule } from 'primeng/confirmdialog';");
            sb.AppendLine("import { ContextMenuModule } from 'primeng/contextmenu';");
            sb.AppendLine("import { VirtualScrollerModule } from 'primeng/virtualscroller';");
            sb.AppendLine("import { DataViewModule } from 'primeng/dataview';");
            sb.AppendLine("import { DialogModule } from 'primeng/dialog';");
            sb.AppendLine("import { DividerModule } from 'primeng/divider';");
            sb.AppendLine("import { DockModule } from 'primeng/dock';");
            sb.AppendLine("import { DragDropModule } from 'primeng/dragdrop';");
            sb.AppendLine("import { DropdownModule } from 'primeng/dropdown';");
            sb.AppendLine("import { DynamicDialogModule } from 'primeng/dynamicdialog';");
            sb.AppendLine("import { EditorModule } from 'primeng/editor';");
            sb.AppendLine("import { FieldsetModule } from 'primeng/fieldset';");
            sb.AppendLine("import { FileUploadModule } from 'primeng/fileupload';");
            sb.AppendLine("import { GalleriaModule } from 'primeng/galleria';");
            sb.AppendLine("import { InplaceModule } from 'primeng/inplace';");
            sb.AppendLine("import { InputMaskModule } from 'primeng/inputmask';");
            sb.AppendLine("import { InputSwitchModule } from 'primeng/inputswitch';");
            sb.AppendLine("import { InputTextModule } from 'primeng/inputtext';");
            sb.AppendLine("import { InputTextareaModule } from 'primeng/inputtextarea';");
            sb.AppendLine("import { InputNumberModule } from 'primeng/inputnumber';");
            sb.AppendLine("import { ImageModule } from 'primeng/image';");
            sb.AppendLine("import { KnobModule } from 'primeng/knob';");
            sb.AppendLine("import { ListboxModule } from 'primeng/listbox';");
            sb.AppendLine("import { MegaMenuModule } from 'primeng/megamenu';");
            sb.AppendLine("import { MenuModule } from 'primeng/menu';");
            sb.AppendLine("import { MenubarModule } from 'primeng/menubar';");
            sb.AppendLine("import { MessageModule } from 'primeng/message';");
            sb.AppendLine("import { MessagesModule } from 'primeng/messages';");
            sb.AppendLine("import { MultiSelectModule } from 'primeng/multiselect';");
            sb.AppendLine("import { OrganizationChartModule } from 'primeng/organizationchart';");
            sb.AppendLine("import { OrderListModule } from 'primeng/orderlist';");
            sb.AppendLine("import { OverlayPanelModule } from 'primeng/overlaypanel';");
            sb.AppendLine("import { PaginatorModule } from 'primeng/paginator';");
            sb.AppendLine("import { PanelModule } from 'primeng/panel';");
            sb.AppendLine("import { PanelMenuModule } from 'primeng/panelmenu';");
            sb.AppendLine("import { PasswordModule } from 'primeng/password';");
            sb.AppendLine("import { PickListModule } from 'primeng/picklist';");
            sb.AppendLine("import { ProgressSpinnerModule } from 'primeng/progressspinner';");
            sb.AppendLine("import { ProgressBarModule } from 'primeng/progressbar';");
            sb.AppendLine("import { RadioButtonModule } from 'primeng/radiobutton';");
            sb.AppendLine("import { RatingModule } from 'primeng/rating';");
            sb.AppendLine("import { SelectButtonModule } from 'primeng/selectbutton';");
            sb.AppendLine("import { SidebarModule } from 'primeng/sidebar';");
            sb.AppendLine("import { ScrollerModule } from 'primeng/scroller';");
            sb.AppendLine("import { ScrollPanelModule } from 'primeng/scrollpanel';");
            sb.AppendLine("import { ScrollTopModule } from 'primeng/scrolltop';");
            sb.AppendLine("import { SkeletonModule } from 'primeng/skeleton';");
            sb.AppendLine("import { SlideMenuModule } from 'primeng/slidemenu';");
            sb.AppendLine("import { SliderModule } from 'primeng/slider';");
            sb.AppendLine("import { SpeedDialModule } from 'primeng/speeddial';");
            sb.AppendLine("import { SpinnerModule } from 'primeng/spinner';");
            sb.AppendLine("import { SplitterModule } from 'primeng/splitter';");
            sb.AppendLine("import { SplitButtonModule } from 'primeng/splitbutton';");
            sb.AppendLine("import { StepsModule } from 'primeng/steps';");
            sb.AppendLine("import { TableModule } from 'primeng/table';");
            sb.AppendLine("import { TabMenuModule } from 'primeng/tabmenu';");
            sb.AppendLine("import { TabViewModule } from 'primeng/tabview';");
            sb.AppendLine("import { TagModule } from 'primeng/tag';");
            sb.AppendLine("import { TerminalModule } from 'primeng/terminal';");
            sb.AppendLine("import { TieredMenuModule } from 'primeng/tieredmenu';");
            sb.AppendLine("import { TimelineModule } from 'primeng/timeline';");
            sb.AppendLine("import { ToastModule } from 'primeng/toast';");
            sb.AppendLine("import { ToggleButtonModule } from 'primeng/togglebutton';");
            sb.AppendLine("import { ToolbarModule } from 'primeng/toolbar';");
            sb.AppendLine("import { TooltipModule } from 'primeng/tooltip';");
            sb.AppendLine("import { TriStateCheckboxModule } from 'primeng/tristatecheckbox';");
            sb.AppendLine("import { TreeModule } from 'primeng/tree';");
            sb.AppendLine("import { TreeSelectModule } from 'primeng/treeselect';");
            sb.AppendLine("import { TreeTableModule } from 'primeng/treetable';");
            sb.AppendLine(" //PRIMENG END ");




            sb.AppendLine("import {CookieService} from 'ngx-cookie-service'; ");
            sb.AppendLine("import {AppService} from './../app.service'; ");
            // sb.AppendLine("import { ReactiveFormsModule } from '@angular/forms'; ")
            sb.AppendLine(" ");
            sb.AppendLine("import { AppComponent } from './app.component'; ");
            sb.AppendLine("import { ROUTING } from './app.routing'; ");
            // sb.AppendLine("import { AboutComponent } from './about/about.component'; ")
            // sb.AppendLine("import { TopnavComponent } from './topnav/topnav.component'; ")
            sb.AppendLine(" ");

         
            for (i = 0; i < MyUtils.cards.Count; i++)
            {

                ot = MyUtils.cards[i];
                sb.AppendLine(" ");
                sb.AppendLine("import { " + ot.Alias + "Component } from './" + ot.Alias + "/" + ot.Alias + ".component'; // " + ot.Name[0].Value);
                Make_AddPartCompServiceToAPP(sb, ot.Sections);
            }


            sb.AppendLine(" ");
            sb.AppendLine("@NgModule({ ");
            sb.AppendLine("    declarations: [ ");
            sb.AppendLine("        AppComponent, ");


            //CardDefinition ot = null;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {

                ot = MyUtils.cards[i];
                sb.AppendLine(" ");
                sb.AppendLine(" " + ot.Alias + "Component ,  // " + ot.Name[0].Value);
                Make_AddCompToAPP(sb, ot.Sections);


            }


            sb.AppendLine("		 ");
            // sb.AppendLine("        AboutComponent, ")
            // sb.AppendLine("        TopnavComponent ")
            sb.AppendLine("		 ");
            sb.AppendLine("    ], ");
            sb.AppendLine("    imports: [ ");
            sb.AppendLine("        BrowserAnimationsModule, ");
            sb.AppendLine("        BrowserModule, ");
            sb.AppendLine("        FormsModule, ");
            // sb.AppendLine("		   ReactiveFormsModule, ")
            sb.AppendLine("        HttpClientModule, ");
            sb.AppendLine("        AppRoutingModule, ");


            // '''''''''' PrimeNG modules imports start
            sb.AppendLine(" 	//PRIMENG");
            sb.AppendLine("    AvatarModule,");
            sb.AppendLine("    AccordionModule,");
            sb.AppendLine("    AutoCompleteModule,");
            sb.AppendLine("    BadgeModule,");
            sb.AppendLine("    BreadcrumbModule,");
            sb.AppendLine("    BlockUIModule,");
            sb.AppendLine("    ButtonModule,");
            sb.AppendLine("    CalendarModule,");
            sb.AppendLine("    CarouselModule,");
            sb.AppendLine("    CascadeSelectModule,");
            sb.AppendLine("    ChartModule,");
            sb.AppendLine("    CheckboxModule,");
            sb.AppendLine("    ChipsModule,");
            sb.AppendLine("    ChipModule,");
            sb.AppendLine("    ColorPickerModule,");
            sb.AppendLine("    ConfirmDialogModule,");
            sb.AppendLine("    ContextMenuModule,");
            sb.AppendLine("    VirtualScrollerModule,");
            sb.AppendLine("    DataViewModule,");
            sb.AppendLine("    DialogModule,");
            sb.AppendLine("    DividerModule,");
            sb.AppendLine("    DockModule,");
            sb.AppendLine("    DragDropModule,");
            sb.AppendLine("    DropdownModule,");
            sb.AppendLine("    DynamicDialogModule,");
            sb.AppendLine("    EditorModule,");
            sb.AppendLine("    FieldsetModule,");
            sb.AppendLine("    FileUploadModule,");
            sb.AppendLine("    GalleriaModule,");
            sb.AppendLine("    InplaceModule,");
            sb.AppendLine("    InputMaskModule,");
            sb.AppendLine("    InputSwitchModule,");
            sb.AppendLine("    InputTextModule,");
            sb.AppendLine("    InputTextareaModule,");
            sb.AppendLine("    InputNumberModule,");
            sb.AppendLine("    ImageModule,");
            sb.AppendLine("    KnobModule,");
            sb.AppendLine("    ListboxModule,");
            sb.AppendLine("    MegaMenuModule,");
            sb.AppendLine("    MenuModule,");
            sb.AppendLine("    MenubarModule,");
            sb.AppendLine("    MessageModule,");
            sb.AppendLine("    MessagesModule,");
            sb.AppendLine("    MultiSelectModule,");
            sb.AppendLine("    OrganizationChartModule,");
            sb.AppendLine("    OrderListModule,");
            sb.AppendLine("    OverlayPanelModule,");
            sb.AppendLine("    PaginatorModule,");
            sb.AppendLine("    PanelModule,");
            sb.AppendLine("    PanelMenuModule,");
            sb.AppendLine("    PasswordModule,");
            sb.AppendLine("    PickListModule,");
            sb.AppendLine("    ProgressSpinnerModule,");
            sb.AppendLine("    ProgressBarModule,");
            sb.AppendLine("    RadioButtonModule,");
            sb.AppendLine("    RatingModule,");
            sb.AppendLine("    SelectButtonModule,");
            sb.AppendLine("    SidebarModule,");
            sb.AppendLine("    ScrollerModule,");
            sb.AppendLine("    ScrollPanelModule,");
            sb.AppendLine("    ScrollTopModule,");
            sb.AppendLine("    SkeletonModule,");
            sb.AppendLine("    SlideMenuModule,");
            sb.AppendLine("    SliderModule,");
            sb.AppendLine("    SpeedDialModule,");
            sb.AppendLine("    SpinnerModule,");
            sb.AppendLine("    SplitterModule,");
            sb.AppendLine("    SplitButtonModule,");
            sb.AppendLine("    StepsModule,");
            sb.AppendLine("    TableModule,");
            sb.AppendLine("    TabMenuModule,");
            sb.AppendLine("    TabViewModule,");
            sb.AppendLine("    TagModule,");
            sb.AppendLine("    TerminalModule,");
            sb.AppendLine("    TieredMenuModule,");
            sb.AppendLine("    TimelineModule,");
            sb.AppendLine("    ToastModule,");
            sb.AppendLine("    ToggleButtonModule,");
            sb.AppendLine("    ToolbarModule,");
            sb.AppendLine("    TooltipModule,");
            sb.AppendLine("    TriStateCheckboxModule,");
            sb.AppendLine("    TreeModule,");
            sb.AppendLine("    TreeSelectModule,");
            sb.AppendLine("    TreeTableModule,");
            sb.AppendLine("    AnimateModule,");
            sb.AppendLine("    CardModule,");
            sb.AppendLine(" 	//PRIMENG END");
            // '''''''''' PrimeNG modules imports end


            sb.AppendLine(" ");
            sb.AppendLine("        ROUTING ");
            sb.AppendLine("    ], ");
            sb.AppendLine("    providers: [HttpClient ");

            //CardDefinition ot = null;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {
                ot = MyUtils.cards[i];
                Make_AddServiceToImportAPP(sb, ot.Sections);
            }
            sb.AppendLine("	,AppService ");
            sb.AppendLine("	,CookieService ");
            sb.AppendLine("	], ");
            sb.AppendLine("    bootstrap: [AppComponent] ");
            sb.AppendLine("}) ");
            sb.AppendLine("export class AppModule { ");
            sb.AppendLine("} ");




            Tool_WriteFile(sb.ToString(), textBoxOutPutFolder.Text + @"\ts\", "app.module.ts", false);


            // ''''''''''''   routing ''''''''''''''
            sb = new StringBuilder();

            sb.AppendLine("import { ModuleWithProviders } from '@angular/core/src/metadata/ng_module'; ");
            sb.AppendLine("import { Routes, RouterModule } from '@angular/router'; ");
            sb.AppendLine(" ");
            sb.AppendLine("import { AboutComponent } from './about/about.component'; ");


            //CardDefinition ot = null;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {

                ot = MyUtils.cards[i];
                sb.AppendLine("import { " + ot.Alias + "Component } from './" + ot.Alias + "/" + ot.Alias + ".component'; ");

            }

            sb.AppendLine("export const ROUTES: Routes = [ ");
            sb.AppendLine("    {path: '', redirectTo: 'home', pathMatch: 'full'}, ");
            sb.AppendLine("	 ");

            //CardDefinition ot = null;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {

                ot = MyUtils.cards[i];
                sb.AppendLine("	{path: '" + ot.Alias + "', component:  " + ot.Alias + "Component}, ");

            }

            sb.AppendLine("	{path: 'home', component: AboutComponent} ");
            sb.AppendLine("]; ");
            sb.AppendLine(" ");
            sb.AppendLine("export const ROUTING: ModuleWithProviders = RouterModule.forRoot(ROUTES);");

            Tool_WriteFile(sb.ToString(), textBoxOutPutFolder.Text + @"\ts\", "app.routing.ts", false);



            // '''''''''''' menu ''''''''''''''
            sb = new StringBuilder();
            sb.AppendLine(" { ");
            sb.AppendLine("  \"data\":[ ");
            sb.AppendLine("	{ ");
            sb.AppendLine("	\"text\": \"Home\", ");
            sb.AppendLine("	\"icon\" : \"pi pi-home fa-fw fa-lg\", ");
            sb.AppendLine("	\"mdbIcon\" : \"home\", ");
            sb.AppendLine("	\"routerLink\" : \"/home\", ");
            sb.AppendLine("	\"selected\":true ");
            sb.AppendLine("	} ");


            isFirst = true;
            //CardDefinition ot = null;
            for (i = 0; i < MyUtils.cards.Count; i++)
            {

                ot = MyUtils.cards[i];
                sb.AppendLine("	,{ ");
                sb.AppendLine("	  \"text\": \"" + ot.Name[0].Value + "\", ");
                //sb.AppendLine("	  \"icon\" : \"fa " + ot.objIconCls + " fa-fw\", ");
                sb.AppendLine("	  \"icon\" : \"fa  fa-fw\", ");
                sb.AppendLine("	  \"routerLink\" : \"/" + ot.Alias + "\" ");
                sb.AppendLine("	} ");

            }

            sb.AppendLine("	] ");
            sb.AppendLine("} ");

            Tool_WriteFile(sb.ToString(), textBoxOutPutFolder.Text + @"\ts\", "sidebar.json", false);

        }


        public void Make_AddCompToAPP(StringBuilder sb, dv21.SectionType[] PCOL)
        {
            int i;
            dv21.SectionType P;
            if (PCOL == null) return;
            var loopTo = (int)PCOL.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = PCOL[i-1];
                sb.AppendLine("  " + MyUtils.C2(P.Alias) + ("Component, // " + P.Name[0].Value));
                Make_AddCompToAPP(sb, P.Section);
            }
        }

        public void Make_AddServiceToImportAPP(StringBuilder sb, dv21.SectionType[] PCOL)
        {
            int i;
            dv21.SectionType P;
            if (PCOL == null) return;
            var loopTo = (int)PCOL.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = PCOL[i-1];
                sb.AppendLine("  ," + MyUtils.C2(P.Alias) + "_Service");
                Make_AddServiceToImportAPP(sb, P.Section);
            }

        }


        public void Make_AddPartCompServiceToAPP(StringBuilder sb, dv21.SectionType[] PCOL)
        {
            int i;
            dv21.SectionType P;
            if (PCOL == null) return;
            var loopTo = (int)PCOL.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = PCOL[i-1];
                sb.AppendLine("import { " + MyUtils.C2(P.Alias) + "Component } from './" + P.Name + "/" + P.Name + (".component'; // " + P.Name[0].Value));
                sb.AppendLine("import { " + MyUtils.C2(P.Alias) + "_Service } from './" + P.Name + ".service'; ");
                Make_AddPartCompServiceToAPP(sb, P.Section);
            }

        }


        public void TypeMake_Component(dv21.CardDefinition ot)
        {
            StringBuilder sb;
            sb = new StringBuilder();


            sb.AppendLine("<p-card   > ");
            sb.AppendLine("  <ng-template pTemplate=\"header\">");
            sb.AppendLine(" <i class=\"fa\"  aria-hidden=\"True\"></i> %typename% ");
            sb.AppendLine("  </ng-template>");

            sb.AppendLine("	<p-tabView  [(activeIndex)]=\"activeIndex\">");

            dv21.SectionType P;
            bool isFirst;
            int i;
            int j;

            isFirst = true;
            var loopTo = (int)ot.Sections.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = ot.Sections[i-1];
                if (isFirst)
                {
                    isFirst = false;
                    sb.AppendLine("		<p-tabPanel  header=\"" + P.Name[0].Value + "\"   >");
                }
                else
                {
                    sb.AppendLine("		<p-tabPanel  header=\"" + P.Name[0].Value + "\"  >");
                }

                sb.AppendLine("			<app-" + MyUtils.C2(P.Alias) + "></app-" + MyUtils.C2(P.Alias) + ">");
                sb.AppendLine("		</p-tabPanel>");
                sb.AppendLine("		");
            }

            sb.AppendLine("	</p-tabView>");

            sb.AppendLine("</p-card>");


            string ss;
            ss = sb.ToString();
            ss = ss.Replace("%typename%", ot.Name[0].Value);
            //ss = ss.Replace("%ns%", txtNS.Text);
            ss = ss.Replace("%type%", ot.Alias);

            Tool_WriteFile(ss, textBoxOutPutFolder.Text + @"\ts\" + ot.Alias + @"\", ot.Alias + ".component.html", false);



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
            //ss = ss.Replace("%ns%", txtNS.Text);
            ss = ss.Replace("%type%", ot.Alias);

            Tool_WriteFile(ss, textBoxOutPutFolder.Text + @"\ts\" + ot.Alias + @"\", ot.Alias + ".component.ts", false);

            Tool_WriteFile(" ", textBoxOutPutFolder.Text + @"\ts\" + ot.Alias + @"\", ot.Alias + ".component.scss", false);

        }


        


        




        

        private string PartMake_TSList(dv21.CardDefinition ot, dv21.SectionType[] pcol)
        {
            StringBuilder sw;
            sw = new StringBuilder();
            dv21.SectionType P;
            dv21.SectionType P1;
            int i;
            int j;
            if (pcol == null) return "";

            var loopTo = (int)pcol.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = pcol[i-1];
                sw.Append("\r\n" + PartMake_TS(ot, P));

                sw.Append(PartMake_TSList(ot, P.Section));

            }
            return sw.ToString();

        }

        private string PartMake_TS(dv21.CardDefinition ot, dv21.SectionType P)
        {
            StringBuilder sw;
            sw = new StringBuilder();
            try
            {

                dv21.FieldType fld;
                dv21.FieldTypeType ft;

                int i;


                bool isroot = false;
                
                isroot = MyUtils.isRootSection(ot, P);
                



                sw.Append("\r\n" + " export interface   " + MyUtils.C2(P.Alias) + " { // " + P.Name[0].Value);

                sw.Append("\r\n" + "\t" + MyUtils.C2(P.Alias) + idConst + ":string; // Primary key field");

                if (!isroot)
                {
                    dv21.SectionType ParentPart;
                    ParentPart = (dv21.SectionType)MyUtils.FindParentSection(ot,P);
                    if(ParentPart != null)
                        // sw.Append(vbCrLf & vbTab & " parentStructRowId :string; // Parent part -> " & ParentPart.Alias)
                        sw.Append("\r\n" + "\t" + " " + MyUtils.C2(ParentPart.Alias) + idConst + " :string // Parent part id -> " + ParentPart.Name[0].Value);
                }


                // For i = 1 To ot.Sections.Length
                // Dim ChildPart As dv21.SectionType
                // ChildPart = ot.Sections[i-1]

                // sw.Append(vbCrLf & vbTab & " public List<" & ChildPart.Name & ">  " & ChildPart.Name.ToLower & " { get; set; } // " & ChildPart.Caption)

                // Next

                // If ot.IsSingleInstance = MTZMetaModel.MTZMetaModel.enumBoolean.Boolean_Net Then
                // sw.Append(vbCrLf & vbTab & " instanceId:string // Document id ")
                // End If

                else if (ot.SingleTone)
                {

                    // все  разделы верхнего уровня  впихиваем в  0 -раздел
                    if (P.ID.Equals(ot.Sections[0].ID))
                    {
                    }

                    // For i = 1 To ot.Sections.Length
                    // Dim SiblingPart As dv21.SectionType
                    // SiblingPart = ot.Sections[i-1]
                    // If SiblingPart.Sequence <> 0 Then
                    // sw.Append(vbCrLf & vbTab & " public List<" & SiblingPart.Name & ">  " & SiblingPart.Name.ToLower & " { get; set; } // " & SiblingPart.Caption)
                    // End If
                    // Next

                    else
                    {
                        var loopTo = (int)ot.Sections.Length;
                        for (i = 1; i <= loopTo; i++)
                        {
                            dv21.SectionType SiblingPart;
                            SiblingPart = ot.Sections[i-1];
                            if (i==1)
                            {
                                // sw.Append(vbCrLf & vbTab & " public System.Guid  " & SiblingPart.Name  & idConst & " { get; set; } // " & SiblingPart.Caption)
                                // sw.Append(vbCrLf & vbTab & "[Required] [ForeignKey(""FK_" & P.Name  & "_Document"")]")
                                sw.Append("\r\n" + "\t" + "  " + MyUtils.C2(SiblingPart.Alias) + idConst + ":string; // " + SiblingPart.Name[0].Value);
                            }
                        }

                    }

                    // sw.Append(vbCrLf & vbTab & " public System.Guid  instanceId { get; set; } // Document id ")
                }





                bool hasRef;
                dv21.FieldType bf;
                bf = null;
                hasRef = false;
                if (P.Field != null)
                {
                    var loopTo1 = (int)P.Field.Length;
                    for (i = 1; i <= loopTo1; i++)
                    {
                        fld = P.Field[i - 1];

                        if (fld.IsBrief)
                        {
                            if (bf is null)
                            {
                                bf = fld;
                            }

                        }
                        ft = (dv21.FieldTypeType)fld.Type;

                        if (TypeStyle(fld) == "scalar")
                        {
                            if (SortType(fld) == "string")
                            {
                                if (ft.ToString().ToLower() == "file")
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string; // " + fld.Name[0].Value);
                                }
                                else if (fld.MaxSpecified)
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string; // " + fld.Name[0].Value);
                                }
                                else
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string; // " + fld.Name[0].Value);

                                }

                            }
                            if (SortType(fld) == "date")
                            {

                                if (ft.ToString().ToLower() == "date")
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string; // " + fld.Name[0].Value);
                                }
                                else if (ft.ToString().ToLower() == "time")
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string;  // " + fld.Name[0].Value);
                                }
                                else if (ft.ToString().ToLower() == "datetime")
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string;  // " + fld.Name[0].Value);
                                }
                                else if (ft.ToString().ToLower() == "birthday")
                                {
                                    sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string;  // " + fld.Name[0].Value);
                                }
                            }

                            if (SortType(fld) == "number")
                            {

                                sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":Number; // " + fld.Name[0].Value);

                            }
                        }


                        if (TypeStyle(fld) == "interval")
                        {

                            sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":Number;  // " + fld.Name[0].Value);


                        }

                        if (TypeStyle(fld) == "enum")
                        {

                            sw.Append("\r\n" + "\t" + " " + MyUtils.C2(fld.Alias) + ":enums.enum_" + MyUtils.C2(fld.Alias) + "; // " + fld.Name[0].Value);
                            sw.Append("\r\n" + "\t" + " " + MyUtils.C2(fld.Alias) + "_name :string; // enum to text for " + fld.Name[0].Value);

                        }

                        if (fld.Reference)
                        {

                            dv21.SectionType refp;
                            refp = MyUtils.ResolveReference(MyUtils.cards, fld.RefSection);
                            if (refp != null)
                            {
                                sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + ":string ; //" + fld.Name[0].Value + " -> " + refp.Name);
                                hasRef = true;
                            }

                        }
                    }

                    if (hasRef)
                    {
                        sw.Append("\r\n" + "\t" + "// add dereference fields ");
                        // '''' add dereference ref field

                        var loopTo2 = (int)P.Field.Length;
                        for (i = 1; i <= loopTo2; i++)
                        {
                            fld = P.Field[i - 1];
                            ft = (dv21.FieldTypeType)fld.Type;

                            if (fld.Reference)
                            {

                                dv21.SectionType refp;
                                refp = MyUtils.ResolveReference(MyUtils.cards, fld.RefSection);
                                sw.Append("\r\n" + "\t" + MyUtils.C2(fld.Alias) + "_name :string; //" + " dereference for " + refp.Name);
                            }
                        }
                    }
                }

                sw.Append("\r\n" + " }");
            }


            catch (Exception ex)
            {
                Debug.Print(ex.Message + "\r\n" + ex.StackTrace);
                // Stop
            }

            return sw.ToString();

        }


        private string PartMake_ServiceList(dv21.CardDefinition ot, dv21.SectionType[] pcol)
        {
            StringBuilder sw;
            sw = new StringBuilder();
            dv21.SectionType P;
            dv21.SectionType P1;
            int i;
            int j;

            if (pcol == null) return "";
            var loopTo = (int)pcol.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = pcol[i-1];
                sw.Append("\r\n" + PartMake_Service(ot, P));

                sw.Append(PartMake_ServiceList(ot, P.Section));

            }
            return sw.ToString();

        }


        private string Make_AppServiceList(dv21.CardDefinition ot, dv21.SectionType[] pcol)
        {
            StringBuilder sw;
            sw = new StringBuilder();
            dv21.SectionType P;
            dv21.SectionType P1;
            int i;
            int j;
            if (pcol == null) return "";

            var loopTo = (int)pcol.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = pcol[i-1];
                sw.Append("\r\n" + Make_AppService(ot, P));

                sw.Append(Make_AppServiceList(ot, P.Section));

            }
            return sw.ToString();

        }



        private string Make_AppComboSupport(dv21.CardDefinition ot, dv21.SectionType[] pcol)
        {
            StringBuilder sb;
            sb = new StringBuilder();
            dv21.SectionType P;
            int i;

            if (pcol == null) return "";
            var loopTo = (int)pcol.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = pcol[i-1];
                sb.AppendLine("	public Combo" + P.Name + ":Array<ComboInfo> = []; ");
                sb.AppendLine("	public get" + P.Name + "(): Observable<ComboInfo[]> { ");
                sb.AppendLine("     let cpHeaders = new HttpHeaders({ 'Content-Type': 'application/json','Authorization': 'Bearer '+ sessionStorage.getItem('auth_token') });");
                sb.AppendLine("		return this.http.get<ComboInfo[]>(this.serviceURL + '/" + P.Name + "/Combo', { headers: cpHeaders }); ");
                sb.AppendLine(" }");
                sb.AppendLine("	public refreshCombo" + P.Name + "() { ");
                sb.AppendLine("	this.get" + P.Name + "().subscribe(Data => {this.Combo" + P.Name + "=Data;});");
                sb.AppendLine(" }");

                sb.Append(Make_AppComboSupport(ot, P.Section));

            }
            return sb.ToString();

        }




        private string Make_AppComboRefresh(dv21.SectionType[] pcol)
        {
            StringBuilder sb;
            sb = new StringBuilder();
            dv21.SectionType P;
            int i;

            if (pcol == null) return "";

            var loopTo = (int)pcol.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = pcol[i-1];
                sb.AppendLine("	this.get" + P.Name + "().subscribe(data => {this.Combo" + P.Name + "=data;}); ");

                sb.Append(Make_AppComboRefresh(P.Section));

            }
            return sb.ToString();

        }


        private string PartMake_Service(dv21.CardDefinition ot, dv21.SectionType P)
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
            if (P.Field != null)
            {
                var loopTo = (int)P.Field.Length;
                for (i = 1; i <= loopTo; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;
                    if (true)  //TypeStyle(fld) =="scalar")
                    {
                        sb.AppendLine("\t" + fld.Alias + ":string = '';");
                    }
                }


                sb.AppendLine("	PageSize:number=10;");
                sb.AppendLine("	PageUrl:string='';");
                sb.AppendLine("    ");
                sb.AppendLine("	//Fetch all %obj%s");
                sb.AppendLine("    getAll_%obj%s(): Observable<%type%.%obj%[]> {");
                sb.AppendLine("		var qry:string;");
                sb.AppendLine("		qry='';");
                sb.AppendLine("		");

                var loopTo1 = (int)P.Field.Length;
                for (i = 1; i <= loopTo1; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;
                    if (true) //TypeStyle(fld) =="scalar")
                    {
                        sb.AppendLine("		if(this." + fld.Alias + "!=''){");
                        sb.AppendLine("			if(qry !='') qry=qry +'&';");
                        sb.AppendLine("			qry='" + fld.Alias + "='+encodeURIComponent(this." + fld.Alias + ")");
                        sb.AppendLine("		}");
                    }
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
                var loopTo2 = (int)P.Field.Length;
                for (i = 1; i <= loopTo2; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;
                    if (true) //TypeStyle(fld) =="scalar")
                    {
                        sb.AppendLine("\t" + "this." + fld.Alias + " = '';");
                    }
                }
                sb.AppendLine("		");
                sb.AppendLine("	}");


            }

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

          
            isroot = MyUtils.isRootSection(ot,P);
          



            bool AddByParent;

            AddByParent = false;


            if (!isroot)
            {
                AddByParent = true;
            }
            else if (! ot.SingleTone )
            {


                //// все  разделы верхнего уровня  впихиваем в  0 -раздел
                //if (P.Sequence != 0L)
                //{
                //    AddByParent = true;
                //}
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
            sb.AppendLine("        return this.http.put(this.serviceURL + '/%obj%/' + %obj%." + MyUtils.C2(P.Alias) + idConst + ", %obj%, { headers: cpHeaders })");
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
            ss = ss.Replace("%obj%", MyUtils.C2(P.Alias));
            ss = ss.Replace("%type%", ot.Alias);

            Tool_WriteFile(ss, textBoxOutPutFolder.Text + @"\ts\", P.Alias + ".service.ts", false);
            return default;

        }




        private string PartMake_Components(dv21.CardDefinition ot, dv21.SectionType[] pcol)
        {
            StringBuilder sw;
            sw = new StringBuilder();
            dv21.SectionType P;
            dv21.SectionType P1;
            int i;
            int j;
            if (pcol == null) return "";
            var loopTo = (int)pcol.Length;
            for (i = 1; i <= loopTo; i++)
            {
                P = pcol[i-1];
                sw.Append("\r\n" + PartMake_Component(ot, P));

                sw.Append(PartMake_Components(ot, P.Section));

            }
            return sw.ToString();

        }

        private string Make_AppService(dv21.CardDefinition ot, dv21.SectionType P)
        {
        
            var ParentPart = P;
            int i;
            bool isFirst = true;
            StringBuilder sb;
            string ss;

            bool isRoot = false;
            bool hasChild;

            if (MyUtils.isRootSection(ot,P))
            {
                if (ot.SingleTone)
                {
                    if (P.ID.Equals(ot.Sections[0].ID) )
                    {
                        isRoot = true;

                        if (ot.Sections.Length > 1L)
                        {
                            hasChild = true;
                        }
                    }
                    else
                    {
                        var loopTo = (int)ot.Sections.Length;
                        for (i = 1; i <= loopTo; i++)
                        {
                            if (i==1)
                            {
                                ParentPart = ot.Sections[i-1];
                                break;
                            }
                        }
                    }

                    if (P.Section.Length > 0L)
                    {
                        hasChild = true;
                    }
                }
                else
                {
                    isRoot = true;
                    if (P.Section.Length > 0L)
                    {
                        hasChild = true;
                    }
                }
            }
            else
            {
                ParentPart = (dv21.SectionType)MyUtils.FindParentSection(ot,P);
                if (P.Section.Length > 0L)
                {
                    hasChild = true;
                }
            }
            sb = new StringBuilder();

            sb.AppendLine("	// support for Selected %type%.%obj%; ");
            sb.AppendLine("	public Last%obj%:%type%.%obj% = {} as %type%.%obj%; ");
            sb.AppendLine("	public Selected%obj% = new BehaviorSubject<%type%.%obj%>({} as %type%.%obj%); ");
            sb.AppendLine("	public pushSelected%obj%(item:%type%.%obj%){ ");
            sb.AppendLine("		//console.log(\"change Selected %obj%\"); ");
            sb.AppendLine("		this.Last%obj%=item; ");
            sb.AppendLine("		this.Selected%obj%.next(item); ");
            sb.AppendLine("		 ");
            sb.AppendLine("	} ");
            sb.AppendLine("	public current%obj% = this.Selected%obj%.asObservable(); ");

            ss = sb.ToString();
            ss = ss.Replace("%obj%", MyUtils.C2(P.Alias));
            ss = ss.Replace("%parent%", MyUtils.C2(ParentPart.Alias));
            //ss = ss.Replace("%ns%", txtNS.Text);
            ss = ss.Replace("%type%", ot.Alias);

            return ss;
        }

        private string PartMake_Component(dv21.CardDefinition ot, dv21.SectionType P)
        {
            
            StringBuilder sb;
            string ss;
            dv21.FieldType fld;
            dv21.SectionType refP;
            dv21.FieldTypeType ft;
            var ParentPart = P;
            int i;
            bool isFirst = true;

            bool isRoot = false;
            var hasChild = default(bool);
            #region init
            if (MyUtils.isRootSection(ot, P))
            {
                if (!ot.SingleTone)
                {
                    //if (P.Sequence == 0L)
                    //{
                    //    isRoot = true;

                    //    if (ot.Sections.Length > 1L)
                    //    {
                    //        hasChild = true;
                    //    }
                    //}
                    //else
                    {
                        var loopTo = (int)ot.Sections.Length;
                        for (i = 1; i <= loopTo; i++)
                        {
                            if (i==1) //  ot.Sections[i-1].Sequence == 0L)
                            {
                                ParentPart = ot.Sections[i-1];
                                break;
                            }
                        }
                    }

                    if (P.Section != null && P.Section.Length > 0L)
                    {
                        hasChild = true;
                    }
                }
                else
                {
                    isRoot = true;
                    if (P.Section != null && P.Section.Length > 0L)
                    {
                        hasChild = true;
                    }
                }
            }
            else
            {
                ParentPart =  MyUtils.FindParentSection(ot, P);
                if (P.Section != null && P.Section.Length > 0L)
                {
                    hasChild = true;
                }
            }
            #endregion

            #region SCSS

            // write style file
            Tool_WriteFile("  ", textBoxOutPutFolder.Text + @"\ts\" + P.Alias + @"\", P.Alias + ".component.scss", false);
            #endregion


            #region component

            // write component file
            sb = new StringBuilder();
            sb.AppendLine("import { Component, OnInit, OnDestroy,  Input, Output, EventEmitter } from \"@angular/core\";");
            sb.AppendLine("import { %obj%_Service } from \"./../%capobj%.service\";");
            sb.AppendLine("import { AppService } from \"./../app.service\";");
            sb.AppendLine("import { Observable, SubscriptionLike as ISubscription} from \"rxjs\";");
            sb.AppendLine("import {  Validators } from \"@angular/forms\";");
            // sb.AppendLine("import { ISubscription} from ""rxjs/Subscription"";")
            sb.AppendLine("");
            sb.AppendLine("import { RemoveHTMLtagPipe } from './../pipes';");
            sb.AppendLine("import { %type% } from \"./../%type%\";");
            sb.AppendLine("import * as XLSX from 'xlsx';");
            sb.AppendLine("");
            sb.AppendLine("const MODE_LIST = 0;");
            sb.AppendLine("const MODE_NEW = 1;");
            sb.AppendLine("const MODE_EDIT = 2;");
            sb.AppendLine("");

            sb.AppendLine("@Component({");
            sb.AppendLine("	   selector: 'app-%obj%',");
            sb.AppendLine("    styleUrls: ['./%capobj%.component.scss'],");
            sb.AppendLine("    templateUrl: './%capobj%.component.html',");
            sb.AppendLine("})");
            sb.AppendLine("export class %obj%Component implements OnInit {");
            sb.AppendLine("");

            sb.AppendLine("    %obj%Array: Array<%type%.%obj%> = [];");
            sb.AppendLine("    opened: boolean = false;");
            sb.AppendLine("    confirmOpened: boolean = false;");
            sb.AppendLine("    mode: Number = MODE_LIST;");
            sb.AppendLine("    current%obj%: %type%.%obj% = {} as %type%.%obj%;");
            sb.AppendLine("    formMsg: string = '';");
            sb.AppendLine("    valid:boolean=true;");
            sb.AppendLine("    errorFlag:boolean=false;");
            sb.AppendLine("    errorMessage:string='';");
            sb.AppendLine("    errorDetail:string='';");



            if (!isRoot)
            {
                sb.AppendLine("   subscription:SubscriptionLike|undefined=undefined;");
            }
            sb.AppendLine("");
            sb.AppendLine("    constructor( private %obj%_Service: %obj%_Service,  public AppService:AppService ) {");
            sb.AppendLine("    }");
            sb.AppendLine("");

            sb.AppendLine("    ngOnInit() {");
            if (!isRoot)
            {
                sb.AppendLine("		   // console.log(\"Subscribe %obj%\"); ");
                sb.AppendLine("        this.subscription=this.AppService.current%parent%.subscribe(si =>{ this.refresh%obj%(); }, error => { this.ShowError(error); } );");
            }
            sb.AppendLine("        this.refresh%obj%();");
            sb.AppendLine("    }");

            // refreshCombo
            dv21.SectionType part;
            sb.AppendLine("    refreshCombo() {");

            if (P.Field != null)
            {
                var loopTo1 = (int)P.Field.Length;
                for (i = 1; i <= loopTo1; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;


                    if (fld.Reference)
                    {
                        //part = (dv21.SectionType)fld.RefSection;
                        sb.AppendLine("     this.AppService.refreshCombo" + fld.RefSection + "();");
                    }



                }
            }

            sb.AppendLine("    }");


            sb.AppendLine("    ngOnDestroy() {");
            if (!isRoot)
            {
                sb.AppendLine("		   // console.log(\"Unsubscribe %obj%\"); ");
                sb.AppendLine("        this.subscription.unsubscribe();");
            }
            sb.AppendLine("    }");
            sb.AppendLine("");
            if (isRoot)
            {
                sb.AppendLine("    refresh%obj%() {");
                sb.AppendLine("		   //console.log(\"refreshing %obj%\"); ");
                sb.AppendLine("        this.%obj%_Service.getAll_%obj%s().subscribe(%obj%Array => { this.%obj%Array = %obj%Array; }, error => { this.ShowError(error); })");
                sb.AppendLine("        this.current%obj% = {} as %type%.%obj%;");
                if (hasChild)
                {
                    sb.AppendLine("       //console.log(\"clear selection for %obj% on refresh\");");
                    sb.AppendLine("        this.AppService.pushSelected%obj%(this.current%obj%);");
                }

                sb.AppendLine("    }");
            }
            else
            {
                sb.AppendLine("    refresh%obj%() {");
                sb.AppendLine("		let item:%type%.%parent%;");
                sb.AppendLine("		item=this.AppService.Last%parent%;");
                sb.AppendLine("		//console.log(\"refreshing %obj%\"); ");
                sb.AppendLine("     this.current%obj% = {} as %type%.%obj%;");
                if (hasChild)
                {
                    sb.AppendLine("     //console.log(\"clear selection for %obj% on refresh\");");
                    sb.AppendLine("     this.AppService.pushSelected%obj%(this.current%obj%);");
                }

                sb.AppendLine("		if(typeof item === 'undefined') { ");
                sb.AppendLine("		   //console.log(\"no parent item for refresh\"); ");
                sb.AppendLine("        this.%obj%_Service.get_%obj%ByParent('0').subscribe(%obj%Array => { this.%obj%Array = %obj%Array; }, error => { this.ShowError(error); })");
                sb.AppendLine("			return; ");
                sb.AppendLine("		} ");

                if (ParentPart != null)
                {
                    sb.AppendLine("		if(typeof item." + MyUtils.C2(ParentPart.Alias) + idConst + "==='undefined') { ");
                    sb.AppendLine("		   //console.log(\"no parent id for refresh\"); ");
                    sb.AppendLine("        this.%obj%_Service.get_%obj%ByParent('0').subscribe(%obj%Array => { this.%obj%Array = %obj%Array; }, error => { this.ShowError(error); })");
                    sb.AppendLine("			return; ");
                    sb.AppendLine("		} ");
                    sb.AppendLine("		if(typeof item." + MyUtils.C2(ParentPart.Alias) + idConst + " === 'string' ) {");
                    sb.AppendLine("        this.%obj%_Service.get_%obj%ByParent(item." + MyUtils.C2(ParentPart.Alias) + idConst + ").subscribe(%obj%Array => { this.%obj%Array = %obj%Array; }, error => { this.ShowError(error); })");
                    sb.AppendLine("      }");
                }

                sb.AppendLine("    }");
            }


            sb.AppendLine("");
            sb.AppendLine("	   ShowError(err:any){");
            sb.AppendLine("		this.errorMessage=err.message;");
            sb.AppendLine("		this.errorDetail=JSON.stringify(err.error);");
            sb.AppendLine("		this.errorFlag=true;");
            sb.AppendLine("	   }");

            sb.AppendLine("");
            sb.AppendLine("	   getData(){");
            sb.AppendLine("		this.refresh%obj%();");
            sb.AppendLine("		return this.%obj%Array ;");
            sb.AppendLine("	   }");
            sb.AppendLine("");
            sb.AppendLine("    onSelect(item: %type%.%obj%) {");
            sb.AppendLine("        this.current%obj% = item;");
            if (hasChild)
            {
                sb.AppendLine("        this.AppService.pushSelected%obj%(item);");
            }
            sb.AppendLine("    }");
            sb.AppendLine("");
            sb.AppendLine("    onNew() {");
            sb.AppendLine("    this.refreshCombo(); ");
            if (!isRoot)
            {
                if (ParentPart != null)
                {
                    sb.AppendLine("      if(typeof ( this.AppService.Last%parent%." + MyUtils.C2(ParentPart.Alias) + idConst + ") === 'string' ) {");
                    sb.AppendLine("        this.current%obj% = {} as %type%.%obj%;");
                    sb.AppendLine("        this.current%obj%." + MyUtils.C2(ParentPart.Alias) + idConst + " = this.AppService.Last%parent%." + MyUtils.C2(ParentPart.Alias) + idConst + ";");
                }
            }
            else
            {
                sb.AppendLine("        this.current%obj% = {} as %type%.%obj%;");
            }
            sb.AppendLine("        this.opened = true;");
            sb.AppendLine("        this.mode = MODE_NEW;");

            sb.AppendLine("        this.formMsg = 'Создать: ';");
            if (!isRoot)
            {
                sb.AppendLine("      }");
            }

            sb.AppendLine("    }");
            sb.AppendLine("");
            sb.AppendLine("    onEdit(item: %type%.%obj%) {");
            sb.AppendLine("    this.refreshCombo(); ");
            sb.AppendLine("        this.opened = true;");
            sb.AppendLine("        this.formMsg = 'Изменить: ';");
            sb.AppendLine("        this.mode = MODE_EDIT;");
            sb.AppendLine("        this.current%obj% = item;");
            sb.AppendLine("    }");
            sb.AppendLine("");
            sb.AppendLine("    onDelete(item: %type%.%obj%) {");
            sb.AppendLine("        this.confirmOpened = true;");
            sb.AppendLine("        this.current%obj% = item;");
            sb.AppendLine("    }");
            sb.AppendLine("");
            sb.AppendLine("    onConfirmDeletion() {");
            sb.AppendLine("        this.confirmOpened = false;");
            sb.AppendLine("        this.%obj%_Service.delete_%obj%ById(this.current%obj%." + MyUtils.C2(P.Alias) + idConst + ").subscribe(data => {this.refresh%obj%(); this.backToList();}, error => { this.ShowError(error); });");
            sb.AppendLine("    }");
            sb.AppendLine("");



            // '''''''''''''''''''''''' clear combo boxes
            if (P.Field != null)
            {
                var loopTo2 = (int)P.Field.Length;
                for (i = 1; i <= loopTo2; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;

                    if (fld.Enum != null && fld.Enum.Length > 0)
                    {

                        if (!fld.NotNull)
                        {
                            sb.AppendLine("	 " + MyUtils.C2(fld.Alias) + @"_clear(){");
                            sb.AppendLine("     this.current%obj%." + MyUtils.C2(fld.Alias) + " = '' ;");
                            sb.AppendLine("	}");
                        }
                    }


                    if (fld.Reference)
                    {

                        if (!fld.NotNull)
                        {
                            sb.AppendLine("	 " + MyUtils.C2(fld.Alias) + "_clear(){");
                            sb.AppendLine("     this.current%obj%." + MyUtils.C2(fld.Alias) + " = '' ;");
                            sb.AppendLine("	}");
                        }
                    }
                }




                sb.AppendLine("    save(item: %type%.%obj%) {");
                sb.AppendLine("        this.valid=true; ");
                // '''''''''''''''''''''''' field validation
                var loopTo3 = (int)P.Field.Length;
                for (i = 1; i <= loopTo3; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;



                    if (SortType(fld) == "string")
                    {
                        if (fld.NotNull)
                        {
                            if (TypeStyle(fld) == "enum")
                            {
                                sb.AppendLine("     if(this.current%obj%." + MyUtils.C2(fld.Alias) + " == undefined ) this.valid=false;");
                            }
                            else if (fld.Reference)
                            {
                                sb.AppendLine("     if(this.current%obj%." + MyUtils.C2(fld.Alias) + " == undefined ) this.valid=false;");
                            }
                            else
                            {
                                sb.AppendLine("     if(this.current%obj%." + MyUtils.C2(fld.Alias) + " == undefined || this.current%obj%." + MyUtils.C2(fld.Alias) + "=='') this.valid=false;");
                            }
                        }
                    }


                    if (SortType(fld) == "date")
                    {
                        if (fld.NotNull)
                        {
                            sb.AppendLine("     if(this.current%obj%." + MyUtils.C2(fld.Alias) + " == undefined ) this.valid=false;");
                        }
                    }

                    if (SortType(fld) == "number")
                    {
                        if (fld.NotNull)
                        {
                            sb.AppendLine("     if(this.current%obj%." + MyUtils.C2(fld.Alias) + " == undefined  ) this.valid=false;");
                        }
                    }

                }
            }

            // '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
            sb.AppendLine("        if (this.valid) {");
            sb.AppendLine("            switch (this.mode) {");
            sb.AppendLine("                case MODE_NEW: {");
            sb.AppendLine("                    this.%obj%_Service.create_%obj%(item)");
            sb.AppendLine("                        .subscribe(data =>{ this.refresh%obj%();this.backToList();}, error => { this.ShowError(error); });");
            sb.AppendLine("                    break;");
            sb.AppendLine("                }");
            sb.AppendLine("                case MODE_EDIT: {");
            sb.AppendLine("                    this.%obj%_Service.update_%obj%( item)");
            sb.AppendLine("                        .subscribe(data => {this.refresh%obj%();this.backToList();}, error => { this.ShowError(error); });");
            sb.AppendLine("                    break;");
            sb.AppendLine("                }");
            sb.AppendLine("                default:");
            sb.AppendLine("                    break;");
            sb.AppendLine("            }");
            sb.AppendLine("        }");
            sb.AppendLine("    }");
            sb.AppendLine("");

            sb.AppendLine(" exportXSLX(): void {");
            sb.AppendLine("        var aoa:any[] = [];");
            sb.AppendLine("/* set column headers at first line */");
            sb.AppendLine("        if(!aoa[0]) aoa[0] = [];");


            
            int fCnt;
            fCnt = 0;
            if (P.Field != null)
            {
                var loopTo4 = (int)P.Field.Length;
                for (i = 1; i <= loopTo4; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;

                    sb.AppendLine("            aoa[0][" + fCnt.ToString() + "]='" + fld.Name[0].Value + "';");
                    fCnt += 1;

                }
            }

            sb.AppendLine("/* fill data to array */");
            sb.AppendLine("        for(var i = 0; i < this.%obj%Array.length; ++i) {");
            sb.AppendLine("            if(!aoa[i+1]) aoa[i+1] = [];");

            fCnt = 0;

            if (P.Field != null)
            {
                var loopTo5 = (int)P.Field.Length;
                for (i = 1; i <= loopTo5; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;

                    isFirst = false;
                    if (SortType(fld) == "string")
                    {
                        if (fld.Reference)
                        {
                            sb.AppendLine("            aoa[i+1][" + fCnt.ToString() + "]=this.%obj%Array[i]." + MyUtils.C2(fld.Alias) + "_name;");
                        }
                        else if (TypeStyle(fld) == "enum")
                        {
                            sb.AppendLine("            aoa[i+1][" + fCnt.ToString() + "]=this.%obj%Array[i]." + MyUtils.C2(fld.Alias) + "_name;");
                        }
                        else
                        {
                            sb.AppendLine("            aoa[i+1][" + fCnt.ToString() + "]=this.%obj%Array[i]." + MyUtils.C2(fld.Alias) + ";");
                        }
                    }
                    if (SortType(fld) == "number")
                    {
                        sb.AppendLine("            aoa[i+1][" + fCnt.ToString() + "]=this.%obj%Array[i]." + MyUtils.C2(fld.Alias) + ";");
                    }
                    if (SortType(fld) == "date")
                    {
                        sb.AppendLine("            aoa[i+1][" + fCnt.ToString() + "]=this.%obj%Array[i]." + MyUtils.C2(fld.Alias) + ";");
                    }
                    fCnt += 1;

                }
            }

            sb.AppendLine("        }");
            sb.AppendLine("		/* generate worksheet */");
            sb.AppendLine("		const ws: XLSX.WorkSheet = XLSX.utils.aoa_to_sheet(aoa);");
            sb.AppendLine("");
            sb.AppendLine("        var wscols = [");

            if (P.Field != null)
            {
                isFirst = true;
                var loopTo6 = (int)P.Field.Length;
                for (i = 1; i <= loopTo6; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;

                    if (!isFirst)
                    {
                        sb.Append(",");
                    }
                    isFirst = false;
                    //if (SortType(fld) =="string")
                    //{
                    //    if (fld.Reference)
                    //    {
                    //        sb.AppendLine("            {wch: 50}");
                    //    }
                    //    else if (ft.TypeStyle ==TypeStyle_Perecislenie)
                    //    {
                    //        sb.AppendLine("            {wch: 30}");
                    //    }
                    //    else if (ft.ToString().ToLower() == "memo")
                    //    {
                    sb.AppendLine("            {wch: 80}");
                    //    }
                    //    else
                    //    {
                    //        sb.AppendLine("            {wch: 64}");
                    //    }
                    //}
                    //if (SortType(fld) =="number")
                    //{
                    //    sb.AppendLine("            {wch: 20}");
                    //}
                    //if (SortType(fld) =="date")
                    //{
                    //    sb.AppendLine("            {wch: 18}");
                    //}


                }
            }

            sb.AppendLine("        ];");
            sb.AppendLine("");
            sb.AppendLine("        ws['!cols'] = wscols;");
            sb.AppendLine("");
            sb.AppendLine("		/* generate workbook and add the worksheet */");
            sb.AppendLine("		const wb: XLSX.WorkBook = XLSX.utils.book_new();");
            sb.AppendLine("        XLSX.utils.book_append_sheet(wb, ws, '%obj%');");
            sb.AppendLine("        ");
            sb.AppendLine("");
            sb.AppendLine("        wb.Props = {");
            sb.AppendLine("            Title: \"%objname%\",");
            sb.AppendLine("            Subject: \"%objname%\",");
            sb.AppendLine("            Company: \"master.bami\",");
            sb.AppendLine("            Category: \"Experimentation\",");
            sb.AppendLine("            Keywords: \"Export service\",");
            sb.AppendLine("            Author: \"master.bami\",");
            sb.AppendLine("	           Manager: \"master.bami\",");
            sb.AppendLine("	           Comments: \"Raw data export\",");
            sb.AppendLine("	           LastAuthor: \"master.bami\",");
            sb.AppendLine("            CreatedDate: new Date(Date.now())");
            sb.AppendLine("        }");
            sb.AppendLine("");
            sb.AppendLine("		/* save to file */");
            sb.AppendLine("		XLSX.writeFile(wb, '%obj%.xlsx');");
            sb.AppendLine("	}");

            sb.AppendLine("    backToList() {");
            sb.AppendLine("        this.opened = false;");
            sb.AppendLine("        this.confirmOpened = false;");
            sb.AppendLine("        this.mode = MODE_LIST;");
            sb.AppendLine("        this.current%obj% = {} as %type%.%obj%;");

            if (hasChild)
            {
                sb.AppendLine("        //console.log(\"clear selection for %obj%\");");
                sb.AppendLine("        this.AppService.pushSelected%obj%(this.current%obj%);");
            }

            sb.AppendLine("    }");
            sb.AppendLine("}");
            sb.AppendLine(" ");

            ss = sb.ToString();
            ss = ss.Replace("%obj%", MyUtils.C2(P.Alias));
            ss = ss.Replace("%capobj%", P.Name[0].Value);
            if(ParentPart != null)
                ss = ss.Replace("%parent%", MyUtils.C2(ParentPart.Alias));
            //ss = ss.Replace("%ns%", txtNS.Text);
            ss = ss.Replace("%type%", ot.Alias);
            ss = ss.Replace("%objname%", ot.Name[0].Value + "::" + P.Name[0].Value);

            Tool_WriteFile(ss, textBoxOutPutFolder.Text + @"\ts\" + P.Alias + @"\", P.Alias + ".component.ts", false);

            #endregion
            #region html

            // write component html

            sb = new StringBuilder();

            sb.AppendLine("<!--Error dialogue-->");
            sb.AppendLine("<p-dialog   [(visible)]=\"errorFlag\"  ");
            sb.AppendLine("            header=\"Ошибка\"");
            sb.AppendLine("			   >");
            sb.AppendLine("	<ng-template pTemplate=\"header\">");
            sb.AppendLine("        <i class=\"pi pi-exclamation-triangle\"></i> Ошибка");
            sb.AppendLine("       </ng-template>");

            sb.AppendLine("		  <span style=\"color:red\">{{errorMessage}}</span>");
            sb.AppendLine(@"	<p-panel    title=""Подробнее..."" >
          {{errorDetail}} ");
            sb.AppendLine("	</p-panel>");

            sb.AppendLine("	<p-panel> ");
            sb.AppendLine("     <p-button    label=\"Ok\" (onClick)=\"errorFlag=false\" icon=\"pi pi-exclamation-triangle\"></p-button>");
            sb.AppendLine("	</p-panel> ");
            sb.AppendLine("</p-dialog>");



            sb.AppendLine("<!-- edit row pane -->	 ");
            sb.AppendLine(" <p-dialog        [(visible)]=\"opened\"      > ");

            // sb.AppendLine(" <p-card    [closable]=""false"" [vertical-position]=""'top'""  [horizontal-position]=""'right'"" [body-height]=""90"" [show-window]=""opened"" [header]=""true"" [footer]=""true"" > ")
            // sb.AppendLine(" <p-card    [closable]=""false""  [show-window]=""opened"" [header]=""true"" [footer]=""true"" > ")

            sb.AppendLine("	  <ng-template pTemplate=\"header\"> ");
            sb.AppendLine("        {{formMsg}} %objname% ");
            sb.AppendLine("<p *ngIf=\"valid==false\" border-color =\"red\" border=\"all\" padding=\"true\" background-color=\"yellow\">");
            sb.AppendLine("	<label font-color=\"red\"  border=\"bottom\">Ошибка заполнения формы</label>");
            sb.AppendLine("</p>");
            sb.AppendLine("       </ng-template> ");


            isFirst = true;
            if (P.Field != null)
            {
                var loopTo7 = (int)P.Field.Length;
                for (i = 1; i <= loopTo7; i++)
                {
                    fld = P.Field[i - 1];
                    ft = (dv21.FieldTypeType)fld.Type;
                    isFirst = false;




                    if (TypeStyle(fld) == "scalar")
                    {

                        if (SortType(fld) == "string")
                        {
                            if (ft.ToString().ToLower() == "textblob")
                            {


                                sb.AppendLine("<label ");
                                if (!fld.NotNull)
                                {
                                    sb.AppendLine("            font-color=\"BLUE\" ");
                                }
                                else
                                {
                                    sb.AppendLine("             font-color=\"BLACK\" ");
                                }
                                // sb.AppendLine(">" & fld.Name[0].Value & "</label>&nbsp;")
                                sb.AppendLine(">" + fld.Name[0].Value + "</label>");

                                // sb.AppendLine("<ngx-wig  ")
                                // sb.AppendLine(" [(ngModel)]=""current%obj%." & DeCap(fld.Name) & """")
                                // sb.AppendLine(" [placeholder]=""'" & fld.Name[0].Value & "'"" ")
                                // sb.AppendLine(" [buttons]=""'bold,italic,link,list1,list2'"">")
                                // sb.AppendLine("</ngx-wig>")


                                sb.AppendLine("<angular-editor  ");
                                sb.AppendLine(" [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"");
                                sb.AppendLine(" [placeholder]=\"" + fld.Name[0].Value + "\" ");
                                sb.AppendLine(" [config]=\"{ editable: true, spellcheck: false, height: 'auto', minHeight: '40', maxHeight: '600', width: 'auto', minWidth: '0' , translate: 'yes', enableToolbar: true, showToolbar: true, sanitize: true, toolbarPosition: 'top' }\">");
                                sb.AppendLine("</angular-editor>");
                            }

                            //else if (fld.TheStyle.Contains("textarea"))
                            //{
                            //    sb.AppendLine("<label ");
                            //    if (! fld.NotNull)
                            //    {
                            //        sb.AppendLine("            font-color=\"BLUE\" ");
                            //    }
                            //    else
                            //    {
                            //        sb.AppendLine("             font-color=\"BLACK\" ");
                            //    }
                            //    sb.AppendLine(">" + fld.Name[0].Value + "</label>");
                            //    sb.AppendLine(" <textarea  pInputTextarea   name =\"" + MyUtils.C2(fld.Alias) + "\" ");
                            //    sb.AppendLine("             placeholder=\"" + fld.Name[0].Value + "\" ");

                            //    if (! fld.NotNull)
                            //    {
                            //    }
                            //    // sb.AppendLine("            [allow-blank]=""true"" ")
                            //    else
                            //    {
                            //        sb.AppendLine("            required  ");
                            //    }
                            //    sb.AppendLine("	            [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"");
                            //    sb.AppendLine("             ");
                            //    sb.AppendLine("              > ");
                            //    sb.AppendLine("</textarea>");
                            //}
                            else
                            {
                                sb.AppendLine("<label ");
                                if (!fld.NotNull)
                                {
                                    sb.AppendLine("            font-color=\"BLUE\" ");
                                }
                                else
                                {
                                    sb.AppendLine("             font-color=\"BLACK\" ");
                                }
                                sb.AppendLine(">" + fld.Name[0].Value + "</label>");
                                sb.AppendLine("                    <input pInputText  name =\"" + MyUtils.C2(fld.Alias) + "\"  ");
                                sb.AppendLine("                    placeholder = \"" + fld.Name[0].Value + "\" ");
                                if (!fld.NotNull)
                                {
                                }
                                // sb.AppendLine("            [allow-blank]=""true"" ")
                                else
                                {
                                    sb.AppendLine("            required ");
                                }
                                sb.AppendLine("                     [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\" />");


                            }



                        }


                        if (SortType(fld) == "date")
                        {

                            sb.AppendLine("<label ");
                            if (!fld.NotNull)
                            {
                                sb.AppendLine("            font-color=\"BLUE\" ");
                            }
                            else
                            {
                                sb.AppendLine("             font-color=\"BLACK\" ");
                            }
                            sb.AppendLine(">" + fld.Name[0].Value + "</label>");
                            sb.AppendLine("  <p-calendar    ");
                            // If ft.ToString().ToLower() = "date" Then
                            sb.AppendLine("        dateFormat=\"dd.mm.yy\"  ");
                            // End If
                            if (ft.ToString().ToLower() == "datetime")
                            {
                                sb.AppendLine("        dateFormat=\"dd.mm.yy\"   [showTime]=\"true\" [showSeconds]=\"false\" ");
                            }
                            if (ft.ToString().ToLower() == "time")
                            {
                                sb.AppendLine("        dateFormat=\"\" [showTime]=\"true\" [showSeconds]=\"false\"  ");
                            }

                            if (!fld.NotNull)
                            {
                            }
                            // sb.AppendLine("            [required]=""false"" ")
                            else
                            {
                                sb.AppendLine("            required ");
                            }

                            sb.AppendLine("        [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"> ");


                            sb.AppendLine(" </p-calendar> ");

                        }

                        if (SortType(fld) == "number")
                        {
                            sb.AppendLine("<label ");
                            if (!fld.NotNull)
                            {
                                sb.AppendLine("            font-color=\"BLUE\" ");
                            }
                            else
                            {
                                sb.AppendLine("             font-color=\"BLACK\" ");
                            }
                            sb.AppendLine(">" + fld.Name[0].Value + "</label>&nbsp;");
                            sb.AppendLine(" <p-inputNumber    name =\"" + MyUtils.C2(fld.Alias) + "\" ");
                            sb.AppendLine("                    placeholder=\"" + fld.Name[0].Value + "\" ");

                            if (!fld.NotNull)
                            {
                            }
                            // sb.AppendLine("            [allow-blank]=""true"" ")
                            else
                            {
                                sb.AppendLine("            required  ");
                            }

                            sb.AppendLine("	 [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"");
                            sb.AppendLine("                    > ");
                            sb.AppendLine(" </p-inputNumber>");

                        }


                    }



                    if (TypeStyle(fld) == "interval")
                    {
                        sb.AppendLine("<label ");
                        if (!fld.NotNull)
                        {
                            sb.AppendLine("            font-color=\"BLUE\" ");
                        }
                        else
                        {
                            sb.AppendLine("            font-color=\"BLACK\" ");
                        }
                        sb.AppendLine(">" + fld.Name[0].Value + "</label>");
                        sb.AppendLine(" <p-inputNumber  name =\"" + MyUtils.C2(fld.Alias) + "\" ");
                        sb.AppendLine("                    placeholder=\"" + fld.Name[0].Value + "\" ");

                        if (!fld.NotNull)
                        {
                        }
                        // sb.AppendLine("            [allow-blank]=""true"" ")
                        else
                        {
                            sb.AppendLine("            required  ");
                        }

                        sb.AppendLine("	 [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"");
                        sb.AppendLine("                    > ");
                        sb.AppendLine(" </p-inputNumber>");

                    }

                    if (TypeStyle(fld) == "enum")
                    {

                        sb.AppendLine("<label ");
                        if (!fld.NotNull)
                        {
                            sb.AppendLine("            font-color=\"BLUE\" ");
                        }
                        else
                        {
                            sb.AppendLine("             font-color=\"BLACK\" ");
                        }
                        sb.AppendLine(">" + fld.Name[0].Value + "</label>&nbsp;");
                        sb.AppendLine("<p-dropdown ");
                        sb.AppendLine("	  name =\"" + MyUtils.C2(fld.Alias) + "\" ");
                        sb.AppendLine("                    placeholder=\"" + fld.Name[0].Value + "\" ");
                        sb.AppendLine("	 [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"");

                        if (!fld.NotNull)
                        {
                        }
                        // sb.AppendLine("            [allow-blank]=""true"" ")
                        else
                        {
                            sb.AppendLine("            required  ");
                        }

                        sb.AppendLine("	 [display-field]=\"'name'\"");
                        sb.AppendLine("	 [value-field]=\"'id'\"");

                        sb.AppendLine("	 [options]=\"AppService.enum" + MyUtils.C2(fld.Alias) + "Combo()\"");
                        sb.AppendLine("	 >");
                        sb.AppendLine("</p-dropdown>");

                        if (!fld.NotNull)
                        {
                            sb.AppendLine(@"  </p-column>
                   <p-column size=""1"" >");
                            sb.AppendLine(" <p-button   label=\"'X'\" (onClick)=\"" + MyUtils.C2(fld.Alias) + "_clear();\"  styleClass=\"p-button-primary\"></p-button>");

                        }

                    }


                    if (fld.Reference)
                    {



                        sb.AppendLine("<label ");
                        if (!fld.NotNull)
                        {
                            sb.AppendLine("            font-color=\"BLUE\" ");
                        }
                        else
                        {
                            sb.AppendLine("             font-color=\"BLACK\" ");
                        }
                        sb.AppendLine(">" + fld.Name[0].Value + "</label>&nbsp;");
                        sb.AppendLine("	 <p-dropdown ");
                        sb.AppendLine("	 placeholder = \"" + fld.Name[0].Value + "\"");
                        sb.AppendLine("	 name =\"" + MyUtils.C2(fld.Alias) + "\"");
                        // sb.AppendLine("	 [field-label]= ""'" & fld.Name[0].Value & "'""")

                        if (!fld.NotNull)
                        {
                        }
                        // sb.AppendLine("            [allow-blank]=""true"" ")
                        else
                        {
                            sb.AppendLine("            required  ");
                        }
                        sb.AppendLine("	 ");
                        sb.AppendLine("	 optionLabel=\"'name'\"");
                        refP = MyUtils.ResolveReference(MyUtils.cards, fld.RefSection);
                        if (refP != null)
                        {
                            sb.AppendLine("	 [options]=\"AppService.Combo" + refP.Name + "\"");
                        }
                        sb.AppendLine("	 ");
                        sb.AppendLine("	 [(ngModel)]=\"current%obj%." + MyUtils.C2(fld.Alias) + "\"");
                        sb.AppendLine("	 >");
                        sb.AppendLine("  </p-dropdown>");

                        if (!fld.NotNull)
                        {
                            sb.AppendLine(@"  </p-column>
                   <p-column size=""1"" >");
                            // '0';
                            sb.AppendLine(" <p-button   label=\"'X'\" (onClick)=\"" + MyUtils.C2(fld.Alias) + "_clear(); \"  styleClass=\"p-button-primary\" ></p-button>");

                        }


                    }

                    sb.AppendLine("	 <ng-template pTemplate=\"footer\">          <div class=\"card flex justify-content-center\">");
                    // sb.AppendLine("		<button type=""button"" class=""btn btn-outline"" (click) = ""opened = false"">Отмена</button> ")
                    sb.AppendLine("     <p-button   label=\"'Отмена'\" (onClick)=\"opened = false;  refresh%obj%();\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-times'\"></p-button>");
                    // sb.AppendLine("		<button type=""submit"" class=""btn btn-primary"" (click)=""save(current%obj%, true)"" >Сохранить</button> ")
                    sb.AppendLine("     &nbsp;<p-button   label=\"'Сохранить'\" (onClick)=\"save(current%obj%)\" styleClass=\"p-button-success\"  icon=\"'pi pi-save'\"></p-button>");
                    sb.AppendLine("	 </div>	</ng-template> ");
                    sb.AppendLine("</p-dialog> ");
                    sb.AppendLine("   ");
                    sb.AppendLine("<!-- list Of row pane --> ");
                    sb.AppendLine("<p-card    visible=\"true\"  > ");
                    sb.AppendLine("    <ng-template pTemplate=\"header\"> ");

                    sb.AppendLine("    <div class=\" flex flex-wrap gap-3 justify-content-center\"> ");
                    if (!isRoot)
                    {
                        if(ParentPart != null)
                         sb.AppendLine("		<p-button  [disabled]=\"AppService.Last%parent%." + MyUtils.C2(ParentPart.Alias) + idConst + "==null\" label=\"'Создать'\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-plus'\" (onClick)=\"onNew()\"></p-button>");
                    }
                    else
                    {
                        sb.AppendLine("		<p-button  label=\"'Создать'\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-plus'\" (onClick)=\"onNew()\"></p-button>");
                    }
                    sb.AppendLine("		&nbsp;<p-button  [disabled]=\"current%obj%." + MyUtils.C2(P.Alias) + idConst + "==null\" label=\"'Изменить'\" (onClick)=\"onEdit(current%obj%)\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-file-edit'\"></p-button>");

                    sb.AppendLine("     &nbsp;<p-button  [disabled]=\"current%obj%." + MyUtils.C2(P.Alias) + idConst + "==null\" label=\"'Удалить'\" (onClick)=\"onDelete(current%obj%)\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-trash'\"></p-button>");

                    sb.AppendLine("     &nbsp;<p-button   label=\"'Обновить'\" (onClick)=\"refresh%obj%()\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-refresh'\"></p-button>");

                    sb.AppendLine("     &nbsp;<p-button   label=\"'Экспорт'\" (onClick)=\"exportXSLX()\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-file-excel'\"></p-button>");
                    sb.AppendLine("  </div>");
                    sb.AppendLine("	 </ng-template> ");


                    sb.AppendLine("		<p-table   ");

                    sb.AppendLine("		  [rows] = \"10\" ");
                    sb.AppendLine("		  selectionMode=\"single\" ");
                    sb.AppendLine("		  [paginator] = \"true\" ");
                    sb.AppendLine("		  [value]=\"%obj%Array\" ");
                    sb.AppendLine("		  [(selection)]=\"current%obj%\" ");
                    sb.AppendLine("		  (onRowSelect)=\"onSelect($event)\"> ");

                    // caption
                    sb.AppendLine("\t" + "<ng-template pTemplate=\"caption\">");
                    sb.AppendLine("\t" + "\t" + "<div Class=\"flex align-items-center justify-content-between\">");
                    sb.AppendLine("\t" + "\t" + "\t" + "\t" + P.Name[0].Value);
                    sb.AppendLine("\t" + "\t" + " </div>");
                    sb.AppendLine("</ng-template>");


                    // header
                    sb.AppendLine(" <ng-template pTemplate=\"header\"> <tr>");

                    isFirst = true;

                    if (P.Field != null)
                    {
                        var loopTo8 = (int)P.Field.Length;
                        for (i = 1; i <= loopTo8; i++)
                        {
                            fld = P.Field[i - 1];
                            ft = (dv21.FieldTypeType)fld.Type;



                            isFirst = false;
                            if (SortType(fld) == "string")
                            {
                                if (fld.Reference)
                                {
                                    sb.AppendLine("		  <th>" + fld.Name[0].Value + "</th> ");
                                }
                                else if (TypeStyle(fld) == "enum")
                                {
                                    sb.AppendLine("		  <th>" + fld.Name[0].Value + "</th> ");
                                }
                                else
                                {
                                    sb.AppendLine("		  <th>" + fld.Name[0].Value + "</th> ");

                                }
                            }
                            if (SortType(fld) == "number")
                            {
                                sb.AppendLine("		  <th>" + fld.Name[0].Value + "</th> ");
                            }
                            if (SortType(fld) == "date")
                            {
                                sb.AppendLine("		  <th>" + fld.Name[0].Value + "</th> ");
                            }

                        }
                    }
                    sb.AppendLine("</tr></ng-template>");


                    // body
                    sb.AppendLine(" <ng-template pTemplate=\"body\" Let-row> <tr>");

                    isFirst = true;
                    if (P.Field != null)
                    {
                        var loopTo9 = (int)P.Field.Length;
                        for (i = 1; i <= loopTo9; i++)
                        {
                            fld = P.Field[i - 1];
                            ft = (dv21.FieldTypeType)fld.Type;


                            isFirst = false;
                            if (SortType(fld) == "string")
                            {
                                if (fld.Reference)
                                {
                                    sb.AppendLine("		  <td>{{row." + MyUtils.C2(fld.Alias) + "_name}}</td> ");
                                }
                                else if (TypeStyle(fld) == "enum")
                                {
                                    sb.AppendLine("		  <td>{{row." + MyUtils.C2(fld.Alias) + "_name}}</td> ");
                                }

                                else if (ft.ToString().ToLower() == "memo" | ft.ToString().ToLower() == "String")
                                {
                                    sb.AppendLine("		  <td>{{((row." + MyUtils.C2(fld.Alias) + ")?((row." + MyUtils.C2(fld.Alias) + ".length>100) ? row." + MyUtils.C2(fld.Alias) + ".substr(0,100)+'...' : row." + MyUtils.C2(fld.Alias) + " ) : '-') | removehtmltag}}");
                                    sb.AppendLine("		  </td> ");
                                }
                                else
                                {

                                    sb.AppendLine("		  <td>{{row." + MyUtils.C2(fld.Alias) + "}}</td> ");


                                }
                            }
                            if (SortType(fld) == "number")
                            {
                                sb.AppendLine("		  <td>{{row." + MyUtils.C2(fld.Alias) + "}}</td> ");
                            }
                            if (SortType(fld) == "date")
                            {
                                sb.AppendLine("		  <td>{{row." + MyUtils.C2(fld.Alias) + "}}</td> ");
                            }

                        }
                    }
                }

                sb.AppendLine("</tr></ng-template>");


                sb.AppendLine("		</p-table> ");

                sb.AppendLine("</p-card> ");
                sb.AppendLine(" ");
                sb.AppendLine("<!-- confirm delete  dialog -->  ");
                sb.AppendLine("<p-dialog     [(visible)]=\"confirmOpened\" [modal]=\"true\"  [draggable]=\"true\" [resizable]=\"false\"  >  ");
                sb.AppendLine("     ");
                sb.AppendLine("    <ng-template pTemplate=\"header\">");
                sb.AppendLine("Удалить строку:  %objname% ?");
                sb.AppendLine("     </ng-template> ");



                isFirst = true;
                string rowStr = "";
                if (P.Field != null)
                {
                    var loopTo10 = (int)P.Field.Length;
                    for (i = 1; i <= loopTo10; i++)
                    {
                        fld = P.Field[i - 1];
                        ft = (dv21.FieldTypeType)fld.Type;

                        if (fld.IsBrief)
                        {

                            if (!isFirst)
                            {
                                rowStr = rowStr + " +'; '+  ";
                            }
                            isFirst = false;

                            if (fld.Reference)
                            {
                                rowStr = rowStr + "current%obj%." + MyUtils.C2(fld.Alias) + "_name";
                            }
                            else
                            {
                                rowStr = rowStr + "current%obj%." + MyUtils.C2(fld.Alias);
                            }



                        }

                    }
                }

                if (!string.IsNullOrEmpty(rowStr))
                {

                    sb.AppendLine("            Удалить запись: {{ ( (" + rowStr + "||'').length >100 ? (" + rowStr + "||'').substr(0,100)+'...' : (" + rowStr + "||'')) | removehtmltag }}?  ");
                }
                else
                {
                    sb.AppendLine("            Удалить запись ?  ");
                }




                sb.AppendLine("	<ng-template pTemplate=\"footer\"> ");
                sb.AppendLine("     <p-button   label=\"'Отмена'\" (onClick)=\"confirmOpened = false\"  styleClass=\"p-button-primary\"  icon=\"'pi pi-times'\"></p-button>");
                sb.AppendLine("     <p-button   label=\"'Удалить'\" (onClick)=\"onConfirmDeletion()\" styleClass=\"p-button-danger\"  icon=\"'pi pi-trash'\"></p-button>");
                sb.AppendLine("	</ng-template> ");
                sb.AppendLine("</p-dialog > ");
                sb.AppendLine(" ");

                ss = sb.ToString();
                ss = ss.Replace("%obj%", MyUtils.C2(P.Alias));
                if(ParentPart != null)
                    ss = ss.Replace("%parent%", MyUtils.C2(ParentPart.Alias));
                ss = ss.Replace("%objname%", ot.Name[0].Value + "::" + P.Name[0].Value);
                ss = ss.Replace("%type%", ot.Alias);

                Tool_WriteFile(ss, textBoxOutPutFolder.Text + @"\ts\" + P.Alias + @"\", P.Alias + ".component.html", false);

                #endregion


            }

            return "";

        }







        private void Tool_WriteFile(string s, string path, string fname, bool Capitalize = false)
        {
            string p;
            p = path;
            if (!p.EndsWith(@"\"))
            {
                p += @"\";
            }
            DirectoryInfo di;
            di = new DirectoryInfo(p);

            if (!di.Exists)
            {
                di.Create();
            }
            if (Capitalize)
            {
                File.WriteAllText(p + fname, s, Encoding.UTF8);
            }
            else
            {
                File.WriteAllText(p + fname, s, Encoding.UTF8);
            }

       
            

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

                case "text":
                    return "String";


                case "unistring":
                    return "String";

                case "fileid":
                    return "TextBlob";

                case "image":
                    return "ImageBlob";


                case "float":
                    return "Float";

                case "double":
                    return "Double";

                case "refid":
                    return "Integer";


            }
            return "Integer";  /*  " + dv21Type +" */


        }

        private string SortType(dv21.FieldType f)
        {


            switch (f.Type.ToString())
            {
                case "int":
                    return "number";

                case "bool":
                    return "string";

                case "datetime":
                    return "date";

                case "enum":
                    return "string";

                case "bitmask":
                    return "string";

                case "uniqueid":
                    return "string";

                case "userid":
                    return "number";

                case "string":
                    return "string";

                case "text":
                    return "string";


                case "unistring":
                    return "string";

                case "fileid":
                    return "string";

                case "image":
                    return "string";


                case "float":
                    return "number";

                case "double":
                    return "number";

                case "refid":
                    return "string";


            }
            return "string"; 

        }


        private string TypeStyle(dv21.FieldType f)
        {

            if (f.Reference)
                return "reference";

            if (f.Enum !=null && f.Enum.Length >0)
                return "enum";


            switch (f.Type.ToString())
            {
                case "int":
                    return "scalar";

                case "bool":
                    return "scalar";

                case "datetime":
                    return "scalar";

                case "enum":
                    return "enum";

                case "bitmask":
                    return "scalar";

                case "uniqueid":
                    return "scalar";

                case "userid":
                    return "scalar";

                case "string":
                    return "scalar";

                case "text":
                    return "scalar";


                case "unistring":
                    return "scalar";

                case "fileid":
                    return "scalar";

                case "image":
                    return "scalar";


                case "float":
                    return "scalar";

                case "double":
                    return "scalar";

                case "refid":
                    return "scalar";


            }
            return "scalar";

        }



    }



}