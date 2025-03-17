using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using dv21;
using dv21_util;
using dv21_xsd;
using dv21_ctl;
using dv21_tl;


namespace dv21_load
{


	/// <summary>
	/// Summary description for Form2.
	/// </summary>
	/// 
	public class Form2 : System.Windows.Forms.Form
	{
		private System.Windows.Forms.ImageList imageList1;
		private System.ComponentModel.IContainer components;
		private System.Windows.Forms.MainMenu mainMenu1;
		private System.Windows.Forms.MenuItem menuItem1;
		private System.Windows.Forms.MenuItem menuItem4;
		private System.Windows.Forms.MenuItem mnuLoad;
		private System.Windows.Forms.MenuItem mnuSave;
		private System.Windows.Forms.MenuItem mnuExit;
		private System.Windows.Forms.MenuItem menuItem2;
		private System.Windows.Forms.TreeView tvStruct;
		private System.Windows.Forms.Splitter splitter1;
		private System.Windows.Forms.Panel panel1;
		private dv21_ctl.ctlCardDefinition pnlCardDefinition;
		private System.Windows.Forms.OpenFileDialog dlgOpen;
		private System.Windows.Forms.SaveFileDialog dlgSave;
		private dv21_ctl.ctlSectionType pnlSectionType;
		private dv21_ctl.ctlFieldType pnlFieldType;
		private dv21_ctl.ctlEnum pnlEnum;
		private dv21_ctl.ctlViewElement pnlViewElement;
		private dv21_ctl.ctlModeType pnlModeType;
		private dv21_ctl.ctlRestrict pnlRestrict;
		private dv21_ctl.ctlAction pnlAction;
		private System.Windows.Forms.ContextMenu mnuSections;
		private System.Windows.Forms.MenuItem mnuSecsAddSec;
		private System.Windows.Forms.ContextMenu mnuSection;
		private System.Windows.Forms.MenuItem mnuSecDel;
		private System.Windows.Forms.MenuItem mnuSecAddFld;
		private System.Windows.Forms.MenuItem mnuSecAddSec;
		private System.Windows.Forms.ContextMenu mnuField;
		private System.Windows.Forms.MenuItem mnuFldDel;
		private System.Windows.Forms.MenuItem mnuFldAddEnum;
		private System.Windows.Forms.ContextMenu mnuEnum;
		private System.Windows.Forms.MenuItem mnuEnumDel;
		private dv21.CardDefinition cd;
		private System.Windows.Forms.MenuItem mnuLoadData;
		private MyTreeNode LastNode;
		private dv21_ctl.ctlviewColumn pnlColumn;
		private System.Windows.Forms.ContextMenu mnuModes;
		private System.Windows.Forms.MenuItem mnuModesAddMode;
		private System.Windows.Forms.ContextMenu mnuMode;
		private System.Windows.Forms.MenuItem mnuModeDel;
		private System.Windows.Forms.MenuItem mnuModeAddRestrict;
		private System.Windows.Forms.ContextMenu mnuRestrict;
		private System.Windows.Forms.MenuItem mnuRestrictDel;
		private System.Windows.Forms.ContextMenu mnuActions;
		private System.Windows.Forms.MenuItem mnuActionsAddAction;
		private System.Windows.Forms.ContextMenu mnuAction;
		private System.Windows.Forms.MenuItem mnuActionDel;
		private System.Windows.Forms.ContextMenu mnuViews;
		private System.Windows.Forms.MenuItem mnuViewsAddView;
		private System.Windows.Forms.ContextMenu mnuView;
		private System.Windows.Forms.MenuItem mnuViewDel;
		private System.Windows.Forms.MenuItem mnuViewAddcolumn;
		private System.Windows.Forms.ContextMenu mnuColumn;
		private System.Windows.Forms.MenuItem mnuColumnDel;
		private System.Windows.Forms.MenuItem mnuRefreshTree;
		private System.Windows.Forms.MenuItem menuItem3;
		private System.Windows.Forms.MenuItem mnuConvertToXSD;
		private System.Windows.Forms.SaveFileDialog dlgSaveXSD;
		private System.Windows.Forms.MenuItem mnuconst_CS;
		private System.Windows.Forms.MenuItem mnuConst_VB;
		private System.Windows.Forms.MenuItem mnuConst_CPP;
		private System.Windows.Forms.MenuItem menuItem5;
		private System.Windows.Forms.UserControl ctl;
        private MenuItem mnuGenPG;
        private SaveFileDialog dlgSaveSQL;
        private MenuItem mnuFieldList;
        private MenuItem mnuJDLGn;
        private SaveFileDialog dlgSaveJDL;
        private SaveFileDialog dlgSaveCSV;
        private MenuItem menuItem6;
        private MenuItem mnuJDLGenAll;
        private MenuItem mnuFieldListAll;
        private MenuItem mnuTypeLib;
        private MenuItem mnuJDL_i18n;
        private FolderBrowserDialog dlgFolder;
        private string LastOpenFile;

		

		public Form2()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//

			cd = new dv21.CardDefinition();
			cd.Name = new LocalizedStringsLocalizedString[1];
			cd.Name[0] = new LocalizedStringsLocalizedString();
 
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form2));
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
            this.menuItem1 = new System.Windows.Forms.MenuItem();
            this.menuItem2 = new System.Windows.Forms.MenuItem();
            this.mnuLoad = new System.Windows.Forms.MenuItem();
            this.mnuLoadData = new System.Windows.Forms.MenuItem();
            this.mnuSave = new System.Windows.Forms.MenuItem();
            this.mnuRefreshTree = new System.Windows.Forms.MenuItem();
            this.menuItem4 = new System.Windows.Forms.MenuItem();
            this.mnuExit = new System.Windows.Forms.MenuItem();
            this.menuItem3 = new System.Windows.Forms.MenuItem();
            this.mnuConvertToXSD = new System.Windows.Forms.MenuItem();
            this.mnuconst_CS = new System.Windows.Forms.MenuItem();
            this.mnuConst_VB = new System.Windows.Forms.MenuItem();
            this.mnuConst_CPP = new System.Windows.Forms.MenuItem();
            this.menuItem5 = new System.Windows.Forms.MenuItem();
            this.mnuGenPG = new System.Windows.Forms.MenuItem();
            this.menuItem6 = new System.Windows.Forms.MenuItem();
            this.mnuJDLGn = new System.Windows.Forms.MenuItem();
            this.mnuJDLGenAll = new System.Windows.Forms.MenuItem();
            this.mnuFieldList = new System.Windows.Forms.MenuItem();
            this.tvStruct = new System.Windows.Forms.TreeView();
            this.splitter1 = new System.Windows.Forms.Splitter();
            this.panel1 = new System.Windows.Forms.Panel();
            this.dlgOpen = new System.Windows.Forms.OpenFileDialog();
            this.dlgSave = new System.Windows.Forms.SaveFileDialog();
            this.mnuSections = new System.Windows.Forms.ContextMenu();
            this.mnuSecsAddSec = new System.Windows.Forms.MenuItem();
            this.mnuSection = new System.Windows.Forms.ContextMenu();
            this.mnuSecDel = new System.Windows.Forms.MenuItem();
            this.mnuSecAddFld = new System.Windows.Forms.MenuItem();
            this.mnuSecAddSec = new System.Windows.Forms.MenuItem();
            this.mnuField = new System.Windows.Forms.ContextMenu();
            this.mnuFldDel = new System.Windows.Forms.MenuItem();
            this.mnuFldAddEnum = new System.Windows.Forms.MenuItem();
            this.mnuEnum = new System.Windows.Forms.ContextMenu();
            this.mnuEnumDel = new System.Windows.Forms.MenuItem();
            this.mnuModes = new System.Windows.Forms.ContextMenu();
            this.mnuModesAddMode = new System.Windows.Forms.MenuItem();
            this.mnuMode = new System.Windows.Forms.ContextMenu();
            this.mnuModeDel = new System.Windows.Forms.MenuItem();
            this.mnuModeAddRestrict = new System.Windows.Forms.MenuItem();
            this.mnuRestrict = new System.Windows.Forms.ContextMenu();
            this.mnuRestrictDel = new System.Windows.Forms.MenuItem();
            this.mnuActions = new System.Windows.Forms.ContextMenu();
            this.mnuActionsAddAction = new System.Windows.Forms.MenuItem();
            this.mnuAction = new System.Windows.Forms.ContextMenu();
            this.mnuActionDel = new System.Windows.Forms.MenuItem();
            this.mnuViews = new System.Windows.Forms.ContextMenu();
            this.mnuViewsAddView = new System.Windows.Forms.MenuItem();
            this.mnuView = new System.Windows.Forms.ContextMenu();
            this.mnuViewDel = new System.Windows.Forms.MenuItem();
            this.mnuViewAddcolumn = new System.Windows.Forms.MenuItem();
            this.mnuColumn = new System.Windows.Forms.ContextMenu();
            this.mnuColumnDel = new System.Windows.Forms.MenuItem();
            this.dlgSaveXSD = new System.Windows.Forms.SaveFileDialog();
            this.dlgSaveSQL = new System.Windows.Forms.SaveFileDialog();
            this.dlgSaveJDL = new System.Windows.Forms.SaveFileDialog();
            this.dlgSaveCSV = new System.Windows.Forms.SaveFileDialog();
            this.mnuFieldListAll = new System.Windows.Forms.MenuItem();
            this.mnuTypeLib = new System.Windows.Forms.MenuItem();
            this.mnuJDL_i18n = new System.Windows.Forms.MenuItem();
            this.dlgFolder = new System.Windows.Forms.FolderBrowserDialog();
            this.pnlColumn = new dv21_ctl.ctlviewColumn();
            this.pnlRestrict = new dv21_ctl.ctlRestrict();
            this.pnlModeType = new dv21_ctl.ctlModeType();
            this.pnlAction = new dv21_ctl.ctlAction();
            this.pnlViewElement = new dv21_ctl.ctlViewElement();
            this.pnlEnum = new dv21_ctl.ctlEnum();
            this.pnlFieldType = new dv21_ctl.ctlFieldType();
            this.pnlSectionType = new dv21_ctl.ctlSectionType();
            this.pnlCardDefinition = new dv21_ctl.ctlCardDefinition();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "");
            this.imageList1.Images.SetKeyName(1, "");
            this.imageList1.Images.SetKeyName(2, "");
            this.imageList1.Images.SetKeyName(3, "");
            this.imageList1.Images.SetKeyName(4, "");
            this.imageList1.Images.SetKeyName(5, "");
            this.imageList1.Images.SetKeyName(6, "");
            this.imageList1.Images.SetKeyName(7, "");
            this.imageList1.Images.SetKeyName(8, "");
            this.imageList1.Images.SetKeyName(9, "");
            this.imageList1.Images.SetKeyName(10, "");
            this.imageList1.Images.SetKeyName(11, "");
            this.imageList1.Images.SetKeyName(12, "");
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.menuItem1,
            this.menuItem3});
            // 
            // menuItem1
            // 
            this.menuItem1.Index = 0;
            this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.menuItem2,
            this.mnuLoad,
            this.mnuLoadData,
            this.mnuSave,
            this.mnuRefreshTree,
            this.menuItem4,
            this.mnuExit});
            this.menuItem1.Text = "File";
            // 
            // menuItem2
            // 
            this.menuItem2.Index = 0;
            this.menuItem2.Text = "New";
            this.menuItem2.Click += new System.EventHandler(this.menuItem2_Click);
            // 
            // mnuLoad
            // 
            this.mnuLoad.Index = 1;
            this.mnuLoad.Text = "Load";
            this.mnuLoad.Click += new System.EventHandler(this.mnuLoad_Click);
            // 
            // mnuLoadData
            // 
            this.mnuLoadData.Index = 2;
            this.mnuLoadData.Text = "Load Test Data";
            this.mnuLoadData.Click += new System.EventHandler(this.mnuLoadData_Click);
            // 
            // mnuSave
            // 
            this.mnuSave.Index = 3;
            this.mnuSave.Text = "Save";
            this.mnuSave.Click += new System.EventHandler(this.mnuSave_Click);
            // 
            // mnuRefreshTree
            // 
            this.mnuRefreshTree.Index = 4;
            this.mnuRefreshTree.Text = "Refresh tree";
            this.mnuRefreshTree.Click += new System.EventHandler(this.mnuRefreshTree_Click);
            // 
            // menuItem4
            // 
            this.menuItem4.Index = 5;
            this.menuItem4.Text = "-";
            // 
            // mnuExit
            // 
            this.mnuExit.Index = 6;
            this.mnuExit.Text = "Exit";
            this.mnuExit.Click += new System.EventHandler(this.mnuExit_Click);
            // 
            // menuItem3
            // 
            this.menuItem3.Index = 1;
            this.menuItem3.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuConvertToXSD,
            this.mnuconst_CS,
            this.mnuConst_VB,
            this.mnuConst_CPP,
            this.menuItem5,
            this.mnuGenPG,
            this.menuItem6,
            this.mnuJDLGn,
            this.mnuJDLGenAll,
            this.mnuFieldList,
            this.mnuFieldListAll,
            this.mnuTypeLib,
            this.mnuJDL_i18n});
            this.menuItem3.Text = "Tools";
            // 
            // mnuConvertToXSD
            // 
            this.mnuConvertToXSD.Index = 0;
            this.mnuConvertToXSD.Text = "Convert to XSD";
            this.mnuConvertToXSD.Click += new System.EventHandler(this.mnuConvertToXSD_Click);
            // 
            // mnuconst_CS
            // 
            this.mnuconst_CS.Index = 1;
            this.mnuconst_CS.Text = "C# constants";
            this.mnuconst_CS.Click += new System.EventHandler(this.mnuconst_CS_Click);
            // 
            // mnuConst_VB
            // 
            this.mnuConst_VB.Index = 2;
            this.mnuConst_VB.Text = "VB constants";
            this.mnuConst_VB.Click += new System.EventHandler(this.mnuConst_VB_Click);
            // 
            // mnuConst_CPP
            // 
            this.mnuConst_CPP.Index = 3;
            this.mnuConst_CPP.Text = "C++ constants";
            this.mnuConst_CPP.Click += new System.EventHandler(this.mnuConst_CPP_Click);
            // 
            // menuItem5
            // 
            this.menuItem5.Index = 4;
            this.menuItem5.Text = "-";
            // 
            // mnuGenPG
            // 
            this.mnuGenPG.Index = 5;
            this.mnuGenPG.Text = "PG script";
            this.mnuGenPG.Click += new System.EventHandler(this.mnuGenPG_Click);
            // 
            // menuItem6
            // 
            this.menuItem6.Index = 6;
            this.menuItem6.Text = "PG script all library";
            this.menuItem6.Click += new System.EventHandler(this.menuItem6_Click);
            // 
            // mnuJDLGn
            // 
            this.mnuJDLGn.Index = 7;
            this.mnuJDLGn.Text = "JDL Generator";
            this.mnuJDLGn.Click += new System.EventHandler(this.mnuJDLGn_Click);
            // 
            // mnuJDLGenAll
            // 
            this.mnuJDLGenAll.Index = 8;
            this.mnuJDLGenAll.Text = "JDL generate all library";
            this.mnuJDLGenAll.Click += new System.EventHandler(this.mnuJDLGenAll_Click);
            // 
            // mnuFieldList
            // 
            this.mnuFieldList.Index = 9;
            this.mnuFieldList.Text = "Field List";
            this.mnuFieldList.Click += new System.EventHandler(this.mnuFieldList_Click);
            // 
            // tvStruct
            // 
            this.tvStruct.Dock = System.Windows.Forms.DockStyle.Left;
            this.tvStruct.ImageIndex = 0;
            this.tvStruct.ImageList = this.imageList1;
            this.tvStruct.Indent = 35;
            this.tvStruct.ItemHeight = 32;
            this.tvStruct.Location = new System.Drawing.Point(0, 0);
            this.tvStruct.Name = "tvStruct";
            this.tvStruct.SelectedImageIndex = 0;
            this.tvStruct.Size = new System.Drawing.Size(224, 561);
            this.tvStruct.TabIndex = 3;
            this.tvStruct.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.tvStruct_AfterSelect);
            this.tvStruct.MouseUp += new System.Windows.Forms.MouseEventHandler(this.tvStruct_MouseUp);
            // 
            // splitter1
            // 
            this.splitter1.Location = new System.Drawing.Point(224, 0);
            this.splitter1.MinExtra = 300;
            this.splitter1.MinSize = 150;
            this.splitter1.Name = "splitter1";
            this.splitter1.Size = new System.Drawing.Size(8, 561);
            this.splitter1.TabIndex = 4;
            this.splitter1.TabStop = false;
            this.splitter1.SplitterMoved += new System.Windows.Forms.SplitterEventHandler(this.splitter1_SplitterMoved);
            // 
            // panel1
            // 
            this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.panel1.Controls.Add(this.pnlColumn);
            this.panel1.Controls.Add(this.pnlRestrict);
            this.panel1.Controls.Add(this.pnlModeType);
            this.panel1.Controls.Add(this.pnlAction);
            this.panel1.Controls.Add(this.pnlViewElement);
            this.panel1.Controls.Add(this.pnlEnum);
            this.panel1.Controls.Add(this.pnlFieldType);
            this.panel1.Controls.Add(this.pnlSectionType);
            this.panel1.Controls.Add(this.pnlCardDefinition);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(232, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(498, 561);
            this.panel1.TabIndex = 5;
            this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            // 
            // dlgOpen
            // 
            this.dlgOpen.DefaultExt = "xml";
            this.dlgOpen.Filter = "XML files|*.xml|AllFiles|*.*";
            this.dlgOpen.ReadOnlyChecked = true;
            this.dlgOpen.RestoreDirectory = true;
            this.dlgOpen.Title = "Select card definition file";
            // 
            // dlgSave
            // 
            this.dlgSave.CreatePrompt = true;
            this.dlgSave.DefaultExt = "xml";
            this.dlgSave.Filter = "XML files|*.xml|AllFiles|*.*";
            this.dlgSave.RestoreDirectory = true;
            this.dlgSave.Title = "Save card definition";
            // 
            // mnuSections
            // 
            this.mnuSections.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuSecsAddSec});
            // 
            // mnuSecsAddSec
            // 
            this.mnuSecsAddSec.Index = 0;
            this.mnuSecsAddSec.Text = "Добавить раздел";
            this.mnuSecsAddSec.Click += new System.EventHandler(this.mnuSecsAddSec_Click);
            // 
            // mnuSection
            // 
            this.mnuSection.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuSecDel,
            this.mnuSecAddFld,
            this.mnuSecAddSec});
            // 
            // mnuSecDel
            // 
            this.mnuSecDel.Index = 0;
            this.mnuSecDel.Text = "Удалить";
            this.mnuSecDel.Click += new System.EventHandler(this.mnuSecDel_Click);
            // 
            // mnuSecAddFld
            // 
            this.mnuSecAddFld.Index = 1;
            this.mnuSecAddFld.Text = "Добваить поле";
            this.mnuSecAddFld.Click += new System.EventHandler(this.mnuSecAddFld_Click);
            // 
            // mnuSecAddSec
            // 
            this.mnuSecAddSec.Index = 2;
            this.mnuSecAddSec.Text = "Добавить раздел";
            this.mnuSecAddSec.Click += new System.EventHandler(this.mnuSecAddSec_Click);
            // 
            // mnuField
            // 
            this.mnuField.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuFldDel,
            this.mnuFldAddEnum});
            // 
            // mnuFldDel
            // 
            this.mnuFldDel.Index = 0;
            this.mnuFldDel.Text = "Удалить поле";
            this.mnuFldDel.Click += new System.EventHandler(this.mnuFldDel_Click);
            // 
            // mnuFldAddEnum
            // 
            this.mnuFldAddEnum.Index = 1;
            this.mnuFldAddEnum.Text = "Добавить возможные значения";
            this.mnuFldAddEnum.Click += new System.EventHandler(this.mnuFldAddEnum_Click);
            // 
            // mnuEnum
            // 
            this.mnuEnum.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuEnumDel});
            this.mnuEnum.Popup += new System.EventHandler(this.mnuEnum_Popup);
            // 
            // mnuEnumDel
            // 
            this.mnuEnumDel.Index = 0;
            this.mnuEnumDel.Text = "Удалить";
            this.mnuEnumDel.Click += new System.EventHandler(this.mnuEnumDel_Click);
            // 
            // mnuModes
            // 
            this.mnuModes.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuModesAddMode});
            // 
            // mnuModesAddMode
            // 
            this.mnuModesAddMode.Index = 0;
            this.mnuModesAddMode.Text = "Добавить режим";
            this.mnuModesAddMode.Click += new System.EventHandler(this.mnuModesAddMode_Click);
            // 
            // mnuMode
            // 
            this.mnuMode.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuModeDel,
            this.mnuModeAddRestrict});
            // 
            // mnuModeDel
            // 
            this.mnuModeDel.Index = 0;
            this.mnuModeDel.Text = "Удалить";
            this.mnuModeDel.Click += new System.EventHandler(this.mnuModeDel_Click);
            // 
            // mnuModeAddRestrict
            // 
            this.mnuModeAddRestrict.Index = 1;
            this.mnuModeAddRestrict.Text = "Добавить ограничение";
            this.mnuModeAddRestrict.Click += new System.EventHandler(this.mnuModeAddRestrict_Click);
            // 
            // mnuRestrict
            // 
            this.mnuRestrict.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuRestrictDel});
            // 
            // mnuRestrictDel
            // 
            this.mnuRestrictDel.Index = 0;
            this.mnuRestrictDel.Text = "Удалить";
            this.mnuRestrictDel.Click += new System.EventHandler(this.mnuRestrictDel_Click);
            // 
            // mnuActions
            // 
            this.mnuActions.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuActionsAddAction});
            // 
            // mnuActionsAddAction
            // 
            this.mnuActionsAddAction.Index = 0;
            this.mnuActionsAddAction.Text = "Добавить метод";
            this.mnuActionsAddAction.Click += new System.EventHandler(this.mnuActionsAddAction_Click);
            // 
            // mnuAction
            // 
            this.mnuAction.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuActionDel});
            this.mnuAction.Popup += new System.EventHandler(this.mnuAction_Popup);
            // 
            // mnuActionDel
            // 
            this.mnuActionDel.Index = 0;
            this.mnuActionDel.Text = "Удалить";
            this.mnuActionDel.Click += new System.EventHandler(this.mnuActionDel_Click);
            // 
            // mnuViews
            // 
            this.mnuViews.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuViewsAddView});
            // 
            // mnuViewsAddView
            // 
            this.mnuViewsAddView.Index = 0;
            this.mnuViewsAddView.Text = "Добавить представление";
            this.mnuViewsAddView.Click += new System.EventHandler(this.mnuViewsAddView_Click);
            // 
            // mnuView
            // 
            this.mnuView.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuViewDel,
            this.mnuViewAddcolumn});
            // 
            // mnuViewDel
            // 
            this.mnuViewDel.Index = 0;
            this.mnuViewDel.Text = "Удалить";
            this.mnuViewDel.Click += new System.EventHandler(this.mnuViewDel_Click);
            // 
            // mnuViewAddcolumn
            // 
            this.mnuViewAddcolumn.Index = 1;
            this.mnuViewAddcolumn.Text = "Добавить  колонку";
            this.mnuViewAddcolumn.Click += new System.EventHandler(this.mnuViewAddcolumn_Click);
            // 
            // mnuColumn
            // 
            this.mnuColumn.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuColumnDel});
            // 
            // mnuColumnDel
            // 
            this.mnuColumnDel.Index = 0;
            this.mnuColumnDel.Text = "Удалить";
            this.mnuColumnDel.Click += new System.EventHandler(this.mnuColumnDel_Click);
            // 
            // dlgSaveXSD
            // 
            this.dlgSaveXSD.CreatePrompt = true;
            this.dlgSaveXSD.DefaultExt = "xsd";
            this.dlgSaveXSD.Filter = "XSD files|*.xsd|AllFiles|*.*";
            this.dlgSaveXSD.RestoreDirectory = true;
            this.dlgSaveXSD.Title = "Save card definition";
            // 
            // dlgSaveSQL
            // 
            this.dlgSaveSQL.DefaultExt = "sql";
            this.dlgSaveSQL.Filter = "SQL files|*.sql|AllFiles|*.*";
            this.dlgSaveSQL.RestoreDirectory = true;
            this.dlgSaveSQL.FileOk += new System.ComponentModel.CancelEventHandler(this.dlgSaveSQL_FileOk);
            // 
            // dlgSaveJDL
            // 
            this.dlgSaveJDL.DefaultExt = "jdl";
            this.dlgSaveJDL.Filter = "JDL files|*.jdl|AllFiles|*.*";
            // 
            // dlgSaveCSV
            // 
            this.dlgSaveCSV.DefaultExt = "jdl";
            this.dlgSaveCSV.Filter = "CSV files|*.csv|AllFiles|*.*";
            // 
            // mnuFieldListAll
            // 
            this.mnuFieldListAll.Index = 10;
            this.mnuFieldListAll.Text = "Field list all library";
            this.mnuFieldListAll.Click += new System.EventHandler(this.mnuFieldListAll_Click);
            // 
            // mnuTypeLib
            // 
            this.mnuTypeLib.Index = 11;
            this.mnuTypeLib.Text = "Type library";
            this.mnuTypeLib.Click += new System.EventHandler(this.mnuTypeLib_Click);
            // 
            // mnuJDL_i18n
            // 
            this.mnuJDL_i18n.Index = 12;
            this.mnuJDL_i18n.Text = "JDL cli localize";
            this.mnuJDL_i18n.Click += new System.EventHandler(this.mnuJDL_i18n_Click);
            // 
            // pnlColumn
            // 
            this.pnlColumn.BackColor = System.Drawing.Color.Green;
            this.pnlColumn.Column = null;
            this.pnlColumn.Location = new System.Drawing.Point(248, 224);
            this.pnlColumn.Name = "pnlColumn";
            this.pnlColumn.Size = new System.Drawing.Size(216, 240);
            this.pnlColumn.TabIndex = 8;
            // 
            // pnlRestrict
            // 
            this.pnlRestrict.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.pnlRestrict.Location = new System.Drawing.Point(224, 192);
            this.pnlRestrict.Name = "pnlRestrict";
            this.pnlRestrict.Restrict = null;
            this.pnlRestrict.Size = new System.Drawing.Size(216, 216);
            this.pnlRestrict.TabIndex = 6;
            // 
            // pnlModeType
            // 
            this.pnlModeType.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.pnlModeType.Location = new System.Drawing.Point(192, 168);
            this.pnlModeType.Mode = null;
            this.pnlModeType.Name = "pnlModeType";
            this.pnlModeType.Size = new System.Drawing.Size(344, 248);
            this.pnlModeType.TabIndex = 5;
            this.pnlModeType.Load += new System.EventHandler(this.pnlModeType_Load);
            // 
            // pnlAction
            // 
            this.pnlAction.Action = null;
            this.pnlAction.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.pnlAction.Location = new System.Drawing.Point(168, 144);
            this.pnlAction.Name = "pnlAction";
            this.pnlAction.Size = new System.Drawing.Size(208, 200);
            this.pnlAction.TabIndex = 7;
            // 
            // pnlViewElement
            // 
            this.pnlViewElement.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.pnlViewElement.Location = new System.Drawing.Point(128, 112);
            this.pnlViewElement.Name = "pnlViewElement";
            this.pnlViewElement.Size = new System.Drawing.Size(216, 248);
            this.pnlViewElement.TabIndex = 4;
            this.pnlViewElement.View = null;
            // 
            // pnlEnum
            // 
            this.pnlEnum.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(255)))), ((int)(((byte)(192)))));
            this.pnlEnum.Enum = null;
            this.pnlEnum.Location = new System.Drawing.Point(96, 80);
            this.pnlEnum.Name = "pnlEnum";
            this.pnlEnum.Size = new System.Drawing.Size(232, 136);
            this.pnlEnum.TabIndex = 3;
            // 
            // pnlFieldType
            // 
            this.pnlFieldType.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(192)))));
            this.pnlFieldType.Field = null;
            this.pnlFieldType.Location = new System.Drawing.Point(56, 56);
            this.pnlFieldType.Name = "pnlFieldType";
            this.pnlFieldType.Size = new System.Drawing.Size(248, 392);
            this.pnlFieldType.TabIndex = 2;
            // 
            // pnlSectionType
            // 
            this.pnlSectionType.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(224)))), ((int)(((byte)(192)))));
            this.pnlSectionType.Location = new System.Drawing.Point(32, 32);
            this.pnlSectionType.Name = "pnlSectionType";
            this.pnlSectionType.Section = null;
            this.pnlSectionType.Size = new System.Drawing.Size(240, 304);
            this.pnlSectionType.TabIndex = 1;
            this.pnlSectionType.Visible = false;
            // 
            // pnlCardDefinition
            // 
            this.pnlCardDefinition.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(192)))));
            this.pnlCardDefinition.cd = null;
            this.pnlCardDefinition.Location = new System.Drawing.Point(8, 8);
            this.pnlCardDefinition.Name = "pnlCardDefinition";
            this.pnlCardDefinition.Size = new System.Drawing.Size(232, 312);
            this.pnlCardDefinition.TabIndex = 0;
            this.pnlCardDefinition.Load += new System.EventHandler(this.pnlCardDefinition_Load);
            // 
            // Form2
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(730, 561);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.splitter1);
            this.Controls.Add(this.tvStruct);
            this.Menu = this.mainMenu1;
            this.MinimumSize = new System.Drawing.Size(600, 600);
            this.Name = "Form2";
            this.Text = "Card Schema Editor";
            this.Closing += new System.ComponentModel.CancelEventHandler(this.Form2_Closing);
            this.Load += new System.EventHandler(this.Form2_Load);
            this.Resize += new System.EventHandler(this.Form2_Resize);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

		}
		#endregion

		private void Form2_Load(object sender, System.EventArgs e)
		{
			ReloadTree(null);
			
			
		}

		private void LoadSection(dv21.SectionType[] Sections, MyTreeNode n)
		{
			MyTreeNode n2;
			int i;
			if (Sections !=null)
			{
				for(i=0;i<Sections.Length ;i++)
				{
					if( Sections[i].Name == null || Sections[i].Name[0]==null)
					{
						Sections[i].Name = new LocalizedStringsLocalizedString [1];
						Sections[i].Name[0] = new LocalizedStringsLocalizedString();

					}

					n2 = new MyTreeNode(Sections[i].Name[0].Value +"("+ Sections[i].Name[0].Language +")",10,10);
					n2.BoundObject =Sections[i];
					n2.NodeContextMenu =mnuSection; 
					n.Nodes.Add(n2);  
					LoadSectionFields(Sections[i],n2);
					LoadSection(Sections[i].Section,n2); 
					
				}
			}
		}

		private void LoadSectionFields(dv21.SectionType s, MyTreeNode n)
		{
			MyTreeNode n2,n3;
			int i,j;
			if (s != null && s.Field != null)
			{
				for(i=0;i<s.Field.Length;i++)
				{
					
					if( s.Field[i].Name == null || s.Field[i].Name[0]==null)
					{
						s.Field[i].Name= new LocalizedStringsLocalizedString[1];
						s.Field[i].Name[0]= new LocalizedStringsLocalizedString();
					}


 				    n2 = new MyTreeNode(s.Field[i].Name[0].Value +"("+s.Field[i].Name[0].Language +")",9,9);
					n2.BoundObject =s.Field[i];
					n2.NodeContextMenu =mnuField; 
					n.Nodes.Add(n2);  

					if(s.Field[i].Enum != null)
					{
						for(j=0;j<s.Field[i].Enum.Length  ;j++)
						{
							n3 = new MyTreeNode(s.Field[i].Enum[j].Name +"("+s.Field[i].Enum[j].Value.ToString()  +")",11,11);			
							n2.Nodes.Add(n3); 
							n3.BoundObject =s.Field[i].Enum[j];		
							n3.NodeContextMenu =mnuEnum;
						}
					}
				}
			}
		}
		

		private void ReloadTree(Object SyncTo)
		{
			MyTreeNode n, n2,n3; 
			int i,j;
			tvStruct.Nodes.Clear(); 

			n = new MyTreeNode("Общая информация",0,0);	
			tvStruct.Nodes.Add(n);
			if (cd == null) return;
			n.BoundObject =cd;		

			n = new MyTreeNode("Разделы",1,1);	
			tvStruct.Nodes.Add(n);
			
			if(cd.Sections != null){
				n.BoundObject = cd.Sections;
				n.NodeContextMenu = mnuSections;
				LoadSection(cd.Sections, n);
			}

			n = new MyTreeNode("Элементы просмотра",2,2);	
			tvStruct.Nodes.Add(n);
			n.BoundObject =cd.ViewElements;
			n.NodeContextMenu= mnuViews ;

			if(cd.ViewElements != null)
			{
				for(i=0;i<cd.ViewElements.Length ;i++)
				{
					if (cd.ViewElements[i]!= null)
					{
						if ( cd.ViewElements[i].Name ==null  || cd.ViewElements[i].Name[0]==null){
							cd.ViewElements[i].Name = new LocalizedStringsLocalizedString[1];
							cd.ViewElements[i].Name[0] = new LocalizedStringsLocalizedString();
						}

						n2 = new MyTreeNode(cd.ViewElements[i].Name[0].Value +"("+ cd.ViewElements[i].Name[0].Language +")" ,8,8);
						
						n2.BoundObject =cd.ViewElements[i];		
						n2.NodeContextMenu = mnuView;
						n.Nodes.Add(n2);  
						if (cd.ViewElements[i].Columns !=null)
						{
							for (j=0;j<cd.ViewElements[i].Columns.Length;  j++)
							{
								if ( cd.ViewElements[i].Columns[j].Name ==null  && cd.ViewElements[i].Columns[j].Name[0]==null)
								{
									cd.ViewElements[i].Columns[j].Name = new LocalizedStringsLocalizedString[1];
									cd.ViewElements[i].Columns[j].Name[0] = new LocalizedStringsLocalizedString();
								}
								n3 = new MyTreeNode(cd.ViewElements[i].Columns[j].Name[0].Value +"("+cd.ViewElements[i].Columns[j].Name[0].Language +")" ,12,12);
								n3.BoundObject =cd.ViewElements[i].Columns[j];		
								n2.Nodes.Add(n3);  
								n3.NodeContextMenu = mnuColumn; 
							}
					
						}
					}
				}
			}

			n = new MyTreeNode("Методы",3,3);	
			tvStruct.Nodes.Add(n);
			n.BoundObject =cd.Actions;
			n.NodeContextMenu = mnuActions; 

			if(cd.Actions  != null)
			{
				for(i=0;i<cd.Actions.Length ;i++)
				{
					if ( cd.Actions[i].Name ==null  || cd.Actions[i].Name[0]==null)
					{
						cd.Actions[i].Name= new LocalizedStringsLocalizedString[1];
						cd.Actions[i].Name[0]= new LocalizedStringsLocalizedString();
					}

					n2 = new MyTreeNode(cd.Actions[i].Name[0].Value +"("+cd.Actions[i].Name[0].Language  +")" ,5,5);
					n.Nodes.Add(n2);  
					n2.BoundObject =cd.Actions[i];
					n2.NodeContextMenu = mnuAction;  
				}
			}


			n = new MyTreeNode("Режимы",4,4);	
			tvStruct.Nodes.Add(n);
			n.BoundObject =cd.Modes;
			n.NodeContextMenu=mnuModes;

			if(cd.Modes   != null)
			{
				for(i=0;i<cd.Modes.Length ;i++)
				{
					if (cd.Modes[i].Name ==null  || cd.Modes[i].Name[0]==null)
					{
						cd.Modes[i].Name= new LocalizedStringsLocalizedString [1];
						cd.Modes[i].Name[0]= new LocalizedStringsLocalizedString();
					}

					n2 = new MyTreeNode(cd.Modes[i].Name[0].Value+"("+cd.Modes[i].Name[0].Language +")" ,6,6);
					n.Nodes.Add(n2);  
					n2.BoundObject =cd.Modes[i];
					n2.NodeContextMenu=mnuMode;
					if (cd.Modes[i].Restrict !=null)
					{
						for(j=0;j<cd.Modes[i].Restrict.Length;j++)
						{
							if(cd.Modes[i].Restrict[j]!=null)
							{
								n3=new MyTreeNode(cd.Modes[i].Restrict[j].Type.ToString()  ,7,7); 
								n2.Nodes.Add(n3); 
								n3.BoundObject =cd.Modes[i].Restrict[j];
								n3.NodeContextMenu=mnuRestrict;
								
							}
						}
					}
				}
			}
			
			if (SyncTo!=null)
			{	
				tvStruct.SelectedNode = SyncToNode(tvStruct.Nodes,SyncTo); 
			}
			
		
		}


		private TreeNode SyncToNode(TreeNodeCollection root, object obj)
		{
			MyTreeNode m; int i; TreeNode result;

			for (i=0;i<root.Count;i++)
			{
				m = (MyTreeNode) root[i];
				if (m.BoundObject ==obj)
				{
					result =m;
					return result;
				}
				result = SyncToNode(m.Nodes,obj);
				if (result!=null) return result;
			}
			return null;
			
		}

		private void mnuLoad_Click(object sender, System.EventArgs e)
		{
			try
			{
				dlgOpen.ShowDialog(); 
				cd =MyUtils.DeSerializeObject(dlgOpen.FileName );
				LastOpenFile = dlgOpen.FileName;
				this.Text = LastOpenFile;
                ReloadTree(cd);
			}
			catch{}

		}

		private void mnuSave_Click(object sender, System.EventArgs e)
		{
			try
			{
                dlgSave.FileName = LastOpenFile;
                dlgSave.ShowDialog();
                
                MyUtils.SerializeObject(dlgSave.FileName ,cd);
			}
			catch{}
		}

		private void menuItem2_Click(object sender, System.EventArgs e)
		{
			cd = new dv21.CardDefinition();
			cd.Name = new LocalizedStringsLocalizedString[1];
			cd.Name[0] = new LocalizedStringsLocalizedString();
			cd.Sections = new SectionType[1] ;
			cd.Sections[0] = new SectionType();
			cd.Sections[0].Name = new LocalizedStringsLocalizedString[1];
			cd.Sections[0].Name[0] = new LocalizedStringsLocalizedString();

            ReloadTree(cd);
		}

		private void mnuLoadData_Click(object sender, System.EventArgs e)
		{
			try
			{
				                
				cd.ID=System.Guid.NewGuid().ToString();
				cd.Name = new dv21.LocalizedStringsLocalizedString[2];
				
				cd.Name[0]= new dv21.LocalizedStringsLocalizedString();
				cd.Name[0].Language ="ru";
				cd.Name[0].Value ="Карточка" ;

				cd.Name[1]= new dv21.LocalizedStringsLocalizedString();
				cd.Name[1].Language ="en";
				cd.Name[1].Value ="Card" ;
				
				cd.Alias ="TestCard";
				cd.Version =1;


				// разделы
				cd.Sections= new dv21.SectionType[10]; 

				int i,j;
				for(i=0;i<10;i++)
				{
					cd.Sections[i] = new dv21.SectionType();
					cd.Sections[i].ID =System.Guid.NewGuid().ToString();
					cd.Sections[i].Type= dv21.SectionTypeType.@struct;
					cd.Sections[i].Alias ="Section" +i;
					cd.Sections[i].Name = new dv21.LocalizedStringsLocalizedString[1];
					cd.Sections[i].Name[0]= new dv21.LocalizedStringsLocalizedString();
					cd.Sections[i].Name[0].Language ="ru";
					cd.Sections[i].Name[0].Value ="Раздел " +i ;


					cd.Sections[i].Field = new dv21.FieldType[10]; 
					for(j=0;j<10;j++)
					{
						cd.Sections[i].Field[j] = new dv21.FieldType();
						cd.Sections[i].Field[j].ID=System.Guid.NewGuid().ToString();
						if (j==0)
						{
							cd.Sections[i].Field[j].Type =dv21.FieldTypeType.@enum  ;
							cd.Sections[i].Field[j].Enum = new dv21.FieldTypeEnum[2];
							cd.Sections[i].Field[j].Enum[0] = new dv21.FieldTypeEnum();
							cd.Sections[i].Field[j].Enum[0].Name ="Enum 1";
							cd.Sections[i].Field[j].Enum[0].Value=1;
							cd.Sections[i].Field[j].Enum[1] = new dv21.FieldTypeEnum();
							cd.Sections[i].Field[j].Enum[1].Name ="Enum 2";
							cd.Sections[i].Field[j].Enum[1].Value=2;
						}
						else
						{
							cd.Sections[i].Field[j].Type =dv21.FieldTypeType.@string ;
						}
						cd.Sections[i].Field[j].Alias ="f" + j;
						cd.Sections[i].Field[j].Max = 20+j;
						cd.Sections[i].Field[j].MaxSpecified =true;
						cd.Sections[i].Field[j].Name = new dv21.LocalizedStringsLocalizedString[1];
						cd.Sections[i].Field[j].Name[0]= new dv21.LocalizedStringsLocalizedString();
						cd.Sections[i].Field[j].Name[0].Language ="ru";
						cd.Sections[i].Field[j].Name[0].Value ="Поле " +j ;
						cd.Sections[i].Field[j].NotNull = (j%2==1?true:false);
						cd.Sections[i].Field[j].NotNullSpecified =true;
					}
				}
					
				// элементы представления
				cd.ViewElements=new dv21.ViewElementType[1];
				cd.ViewElements[0]= new dv21.ViewElementType();
				cd.ViewElements[0].ID = System.Guid.NewGuid().ToString();
				cd.ViewElements[0].Default =true;
				cd.ViewElements[0].DefaultSpecified= true;
				cd.ViewElements[0].Columns   = new dv21.ViewColumnType[5];
				
				for(j=0;j<5;j++)
				{
					cd.ViewElements[0].Columns[j] = new dv21.ViewColumnType();
					cd.ViewElements[0].Columns[j].ID =System.Guid.NewGuid().ToString(); 	
					cd.ViewElements[0].Columns[j].Alias ="col" + j;

					cd.ViewElements[0].Columns[j].Name = new dv21.LocalizedStringsLocalizedString[1];
					cd.ViewElements[0].Columns[j].Name[0]= new dv21.LocalizedStringsLocalizedString();
					cd.ViewElements[0].Columns[j].Name[0].Language ="ru";
					cd.ViewElements[0].Columns[j].Name[0].Value ="Колонка " +j;
				}

				cd.ViewElements[0].Name = new dv21.LocalizedStringsLocalizedString[1];
				cd.ViewElements[0].Name[0]= new dv21.LocalizedStringsLocalizedString();
				cd.ViewElements[0].Name[0].Language ="ru";
				cd.ViewElements[0].Name[0].Value ="Основной вид";

				// действия

				cd.Actions = new dv21.ActionType[5];
				for(i=0;i<5;i++)
				{
					cd.Actions[i] =  new dv21.ActionType();
					cd.Actions[i].ID = System.Guid.NewGuid().ToString();
					cd.Actions[i].Name = new dv21.LocalizedStringsLocalizedString[1];
					cd.Actions[i].Name[0]= new dv21.LocalizedStringsLocalizedString();
					cd.Actions[i].Name[0].Language ="ru";
					cd.Actions[i].Name[0].Value ="Действие " +i;

				}

				// режимы
				cd.Modes=new dv21.ModeType[1];
				cd.Modes[0]= new dv21.ModeType();
				cd.Modes[0].ID = System.Guid.NewGuid().ToString();
				cd.Modes[0].AllowAllActions =true;
				cd.Modes[0].AllowAllActionsSpecified =true;
				
				cd.Modes[0].Name = new dv21.LocalizedStringsLocalizedString[1];
				cd.Modes[0].Name[0]= new dv21.LocalizedStringsLocalizedString();
				cd.Modes[0].Name[0].Language ="ru";
				cd.Modes[0].Name[0].Value ="По умолчанию";

				// ограничение
				cd.Modes[0].Restrict =new dv21.ModeTypeRestrict[1];
				cd.Modes[0].Restrict[0] =new dv21.ModeTypeRestrict();
				cd.Modes[0].Restrict[0].Type=dv21.ModeTypeRestrictType.section ;
				cd.Modes[0].Restrict[0].ID =cd.Sections[0].ID;
				cd.Modes[0].Restrict[0].AllowCreate =true;
				cd.Modes[0].Restrict[0].AllowCreateSpecified=true;
				cd.Modes[0].Restrict[0].AllowRead =true;
				cd.Modes[0].Restrict[0].AllowReadSpecified =true;
					
			} 
			catch
			{

			}

    		ReloadTree(cd);
			
		}

		private void prop_Click(object sender, System.EventArgs e)
		{
		
		}

		
		private void tvStruct_AfterSelect(object sender, System.Windows.Forms.TreeViewEventArgs e)
		{
			MyTreeNode n = (MyTreeNode)tvStruct.SelectedNode;
			if (n!=null && n.BoundObject !=null)
			{
				//this.Text =n.BoundObject.GetType().ToString();
				
				pnlCardDefinition.Visible = false;
				pnlSectionType.Visible = false;
				pnlFieldType.Visible=false;
				pnlEnum.Visible=false;
				pnlViewElement.Visible = false;
				pnlModeType.Visible = false;
				pnlAction.Visible = false;
				pnlRestrict.Visible = false;
				pnlColumn.Visible = false;
				

				pnlCardDefinition.LastNode=n;
				pnlSectionType.LastNode=n;
				pnlFieldType.LastNode=n;
				pnlEnum.LastNode=n;
				pnlViewElement.LastNode=n;
				pnlModeType.LastNode=n;
				pnlAction.LastNode=n;
				pnlRestrict.LastNode=n;
				pnlColumn.LastNode=n;


				ctl=null;

				switch (n.BoundObject.GetType().ToString())
				{
					case "dv21.CardDefinition":
					{
						
						pnlCardDefinition.cd= cd;
						ctl =pnlCardDefinition;
						break;
					}

					case "dv21.SectionType[]":
						break;

					case "dv21.SectionType":
						pnlSectionType.Section=(dv21.SectionType)n.BoundObject;
						ctl =pnlSectionType;
						break;

					case "dv21.FieldType":
						pnlFieldType.Field = (dv21.FieldType) n.BoundObject;
						ctl=pnlFieldType;
						break;

					case "dv21.FieldTypeEnum":
						pnlEnum.Enum=(dv21.FieldTypeEnum)n.BoundObject;
						ctl=pnlEnum;
						break;

					case "dv21.ViewElementType[]":
						break;

					case "dv21.ViewElementType":
						pnlViewElement.View =(dv21.ViewElementType) n.BoundObject;
						ctl=pnlViewElement;
						break;


					case "dv21.ViewColumnType":
						pnlColumn.Column = (dv21.ViewColumnType) n.BoundObject;
						ctl=pnlColumn;
						break;

					case "dv21.ActionType[]":
						break;

					case "dv21.ActionType":
						pnlAction.Action =(dv21.ActionType) n.BoundObject;
						ctl=pnlAction;
						break;

					case "dv21.ModeType[]":
						break;


					case "dv21.ModeType":
						pnlModeType.Mode =(dv21.ModeType) n.BoundObject; 
						ctl=pnlModeType;
						break;

					case "dv21.ModeTypeRestrict":
						pnlRestrict.Restrict =(dv21.ModeTypeRestrict) n.BoundObject;
						ctl=pnlRestrict;
						break;

					default:
						System.Windows.Forms.MessageBox.Show(n.BoundObject.GetType().ToString());
						break;
				};
				if (ctl != null)
				{
					ctl.Visible=true;
					ctl.Top=0;
					ctl.Left=0;
					ctl.Width=panel1.Width;
					ctl.Height=panel1.Height;
					ctl.ForeColor=panel1.ForeColor ;
					ctl.BackColor=panel1.BackColor ;
				}

			}	
				

		}

		private void mnuExit_Click(object sender, System.EventArgs e)
		{
			System.Windows.Forms.DialogResult  b=
				System.Windows.Forms.MessageBox.Show("Save changes befor exit?","Closing...",System.Windows.Forms.MessageBoxButtons.YesNoCancel,System.Windows.Forms.MessageBoxIcon.Question);
 
			if (b == System.Windows.Forms.DialogResult.Yes)
			{ 
				mnuSave_Click(sender,e);
				Application.Exit(); 
			}
			if (b == System.Windows.Forms.DialogResult.No)
			{ 
				Application.Exit(); 
			}

		}

		private void Form2_Closing(object sender, System.ComponentModel.CancelEventArgs e)
		{
			System.Windows.Forms.DialogResult  b=
				System.Windows.Forms.MessageBox.Show("Save changes befor exit?","Closing...",System.Windows.Forms.MessageBoxButtons.YesNoCancel,System.Windows.Forms.MessageBoxIcon.Question);
 
			if (b == System.Windows.Forms.DialogResult.Yes)
			{ 
				mnuSave_Click(sender,e);
				
			}
			if (b == System.Windows.Forms.DialogResult.Cancel )
			{ 
				e.Cancel =true;
			}
		}

		private void panel1_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
		
		}

		private void tvStruct_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			MyTreeNode mn;
			mn = (MyTreeNode)tvStruct.SelectedNode;
			
			if (mn!=null )
					if (e.Button == System.Windows.Forms.MouseButtons.Right) 
					{
						if (mn.NodeContextMenu != null  )
						{
							LastNode = mn;  
							System.Drawing.Point pt = new System.Drawing.Point(e.X,e.Y);
							mn.NodeContextMenu.Show(tvStruct,pt);
						}
					}
			}

		private void mnuEnum_Popup(object sender, System.EventArgs e)
		{
		
		}

		private void mnuEnumDel_Click(object sender, System.EventArgs e)
		{
			MyTreeNode mn;
			dv21.FieldType ft;

			mn =(MyTreeNode) LastNode.Parent;
			ft=(dv21.FieldType) mn.BoundObject;
			
			ft.Enum = (FieldTypeEnum[])MyUtils.Remove(ft.Enum , LastNode.BoundObject,new FieldTypeEnum[ft.Enum.Length-1]);  
			ReloadTree(ft);
		}

		

		private void pnlCardDefinition_Load(object sender, System.EventArgs e)
		{
		
		}

		private void mnuSecsAddSec_Click(object sender, System.EventArgs e)
		{
			SectionType st =new SectionType();
			st.Name = new LocalizedStringsLocalizedString[1];
			st.Name[0]=new LocalizedStringsLocalizedString();
			if (cd.Sections !=null)
				cd.Sections=(SectionType[])MyUtils.Add( cd.Sections ,st, new SectionType[cd.Sections.Length+1]);
			else
			{
				cd.Sections = new SectionType[1];
				cd.Sections[0]=st;
			}
			
			ReloadTree(st);
		}

		

		private void mnuSecDel_Click(object sender, System.EventArgs e)
		{
			try
			{
				SectionType st = (SectionType) LastNode.BoundObject;
				SectionType[] sta;
				MyTreeNode pnode;
				if(st!=null)
				{
					pnode=(MyTreeNode)LastNode.Parent; 
					
				
					switch (pnode.BoundObject.GetType().ToString())
					{

						case "dv21.SectionType[]":
							sta=(SectionType[])pnode.BoundObject;
							cd.Sections=(SectionType[])MyUtils.Remove(sta,st,new SectionType[sta.Length -1]);	
					        ReloadTree(cd.Sections);
							break;

						case "dv21.SectionType":
							
							sta=((SectionType)pnode.BoundObject).Section ;
							((SectionType)pnode.BoundObject).Section =(SectionType[])MyUtils.Remove(sta,st,new SectionType[sta.Length -1]);	
							ReloadTree(pnode.BoundObject);
							break;
					}
					

				}
			}
			catch{}
			
		}

		private void mnuSecAddSec_Click(object sender, System.EventArgs e)
		{
			SectionType stp,st =new SectionType();
			st.Name = new LocalizedStringsLocalizedString[1];
			st.Name[0]=new LocalizedStringsLocalizedString();
			st.Name[0].Language="ru";
			st.Name[0].Value="Название";
			stp =(SectionType)LastNode.BoundObject; 
			if (stp.Section ==null)
			{
				stp.Section = new SectionType[1];
				stp.Section[0]=st;
			}
			else
			{
				stp.Section =(SectionType[])MyUtils.Add( stp.Section ,st, new SectionType[stp.Section.Length+1]);
			}
			ReloadTree(st);
		}

		private void mnuSecAddFld_Click(object sender, System.EventArgs e)
		{
			SectionType stp;
			FieldType ft = new FieldType();
			ft.Name = new LocalizedStringsLocalizedString[1];
			ft.Name[0]=new LocalizedStringsLocalizedString();
			ft.Name[0].Language="ru";
			ft.Name[0].Value="Название";

			stp =(SectionType)LastNode.BoundObject; 
			if (stp.Field  ==null)
			{
				stp.Field  = new FieldType [1];
				stp.Field[0]=ft;
			}
			else
			{
				stp.Field  =(FieldType[])MyUtils.Add( stp.Field ,ft, new FieldType[stp.Field.Length+1]);
			}
			ReloadTree(ft);
		}

		private void mnuFldDel_Click(object sender, System.EventArgs e)
		{
			try
			{
				FieldType ft = (FieldType) LastNode.BoundObject;
				SectionType sta;
				MyTreeNode pnode;
   			    pnode=(MyTreeNode)LastNode.Parent; 
				sta=((SectionType)pnode.BoundObject) ;
				sta.Field  =(FieldType[])MyUtils.Remove(sta.Field,ft,new FieldType[sta.Field.Length -1]);	
				ReloadTree(sta);

			}
			catch{}
		}

		private void Form2_Resize(object sender, System.EventArgs e)
		{

			if(ctl !=null)
			{
				try
				{
					if(splitter1.MinExtra  >panel1.Width + 20)
					{
						
						tvStruct.Width = this.Width -splitter1.MinExtra-20;
					}
					ctl.Top=0;
					ctl.Left=0;
					ctl.Width=panel1.Width;
					ctl.Height=panel1.Height;		
					
				}
				catch{}
			}
		}

		private void splitter1_SplitterMoved(object sender, System.Windows.Forms.SplitterEventArgs e)
		{
			if(ctl !=null)
			{
				try
				{
					ctl.Top=0;
					ctl.Left=0;
					ctl.Width=panel1.Width;
					ctl.Height=panel1.Height;		
				}
				catch{}
			}
		}

		private void mnuFldAddEnum_Click(object sender, System.EventArgs e)
		{
			FieldTypeEnum en = new FieldTypeEnum() ;
			FieldType ft;
			en.Name ="Enum item";
			en.Value =0;

			ft =(FieldType)LastNode.BoundObject; 
			if (ft.Enum   ==null)
			{
				ft.Enum  = new FieldTypeEnum [1];
				ft.Enum[0]=en;
			}
			else
			{
				ft.Enum  =(FieldTypeEnum[])MyUtils.Add( ft.Enum ,en, new FieldTypeEnum[ft.Enum.Length+1]);
			}
			ReloadTree(en);
		}

		private void mnuModesAddMode_Click(object sender, System.EventArgs e)
		{
			ModeType mt;
			mt = new ModeType();
			mt.Name = new LocalizedStringsLocalizedString[1];
			mt.Name[0]=new LocalizedStringsLocalizedString();
			
			if (cd.Modes!=null)
			{
				cd.Modes  = (ModeType[])MyUtils.Add(cd.Modes,mt,new ModeType[cd.Modes.Length+1]);	
			}
			else
			{
				cd.Modes  = new ModeType[1];
				cd.Modes[0] = mt;
			}
			ReloadTree(mt);
		}

		private void mnuActionsAddAction_Click(object sender, System.EventArgs e)
		{
			ActionType mt;
			mt = new ActionType();
			mt.Name = new LocalizedStringsLocalizedString[1];
			mt.Name[0]=new LocalizedStringsLocalizedString();
			
			if (cd.Actions !=null)
			{
				cd.Actions  = (ActionType[])MyUtils.Add(cd.Actions,mt,new ActionType[cd.Actions.Length+1]);	
			}
			else
			{
				cd.Actions  = new ActionType[1];
				cd.Actions[0] = mt;
			}
			ReloadTree(mt);
		}

		private void mnuViewsAddView_Click(object sender, System.EventArgs e)
		{
			ViewElementType  st =new ViewElementType();
			st.Name = new LocalizedStringsLocalizedString[1];
			st.Name[0]=new LocalizedStringsLocalizedString();
			if (cd.ViewElements  !=null)
				cd.ViewElements=(ViewElementType[])MyUtils.Add( cd.ViewElements ,st, new ViewElementType[cd.ViewElements.Length+1]);
			else
			{
				cd.ViewElements = new ViewElementType[1];
				cd.ViewElements[0]=st;
			}
			
			ReloadTree(st);
		}

		private void mnuRefreshTree_Click(object sender, System.EventArgs e)
		{
			ReloadTree(null);		
		}

		private void mnuActionDel_Click(object sender, System.EventArgs e)
		{
			ActionType st = (ActionType) LastNode.BoundObject;
			if(st!=null)
			{
				cd.Actions=(ActionType[])MyUtils.Remove(cd.Actions,st,new ActionType[cd.Actions.Length -1]);	
				ReloadTree(cd.Actions);
			}
		}

		private void mnuViewDel_Click(object sender, System.EventArgs e)
		{
			ViewElementType st = (ViewElementType) LastNode.BoundObject;
			if(st!=null)
			{
				cd.ViewElements =(ViewElementType[])MyUtils.Remove(cd.ViewElements,st,new ViewElementType[cd.ViewElements.Length -1]);	
				ReloadTree(cd.ViewElements);
			}

		}

		private void mnuModeDel_Click(object sender, System.EventArgs e)
		{
			ModeType st = (ModeType) LastNode.BoundObject;
			if(st!=null)
			{
				cd.Modes =(ModeType[])MyUtils.Remove(cd.Modes,st,new ModeType[cd.Modes.Length -1]);	
				ReloadTree(cd.Modes);
			}
		
		}

		private void mnuModeAddRestrict_Click(object sender, System.EventArgs e)
		{
			ModeType st = (ModeType) LastNode.BoundObject;
			ModeTypeRestrict r;
			r = new ModeTypeRestrict(); 

			if(st!=null)
			{
				if (st.Restrict !=null)
				{
					st.Restrict=(ModeTypeRestrict[])MyUtils.Add( st.Restrict  ,r, new ModeTypeRestrict[st.Restrict.Length+1]);
				}
				else
				{
					st.Restrict = new ModeTypeRestrict[1];
					st.Restrict[0]=r;
				}
				ReloadTree(r);
			}
		}

		private void mnuRestrictDel_Click(object sender, System.EventArgs e)
		{
			MyTreeNode parent;
			parent = (MyTreeNode)LastNode.Parent; 
			ModeType st = (ModeType) parent.BoundObject;
			ModeTypeRestrict r =(ModeTypeRestrict)LastNode.BoundObject ;
			

			if(st!=null)
			{
				st.Restrict=(ModeTypeRestrict[])MyUtils.Remove( st.Restrict  ,r, new ModeTypeRestrict[st.Restrict.Length-1]);
				ReloadTree(r);
			}

		}

		private void mnuViewAddcolumn_Click(object sender, System.EventArgs e)
		{
			ViewElementType st = (ViewElementType) LastNode.BoundObject;
			ViewColumnType 	r;
			r = new ViewColumnType(); 
			r.Name = new LocalizedStringsLocalizedString[1]; 
			r.Name[0]=new LocalizedStringsLocalizedString();

			if(st!=null)
			{
				if (st.Columns  !=null)
				{
					st.Columns=(ViewColumnType[])MyUtils.Add( st.Columns  ,r, new ViewColumnType[st.Columns.Length+1]);
				}
				else
				{
					st.Columns = new ViewColumnType[1];
					st.Columns[0]=r;
				}
				ReloadTree(r);
			}
		}

		private void mnuColumnDel_Click(object sender, System.EventArgs e)
		{
			MyTreeNode parent;
			parent = (MyTreeNode)LastNode.Parent; 
			ViewElementType st = (ViewElementType) parent.BoundObject;
			ViewColumnType r =(ViewColumnType)LastNode.BoundObject ;
			

			if(st!=null)
			{
				st.Columns =(ViewColumnType[])MyUtils.Remove( st.Columns  ,r, new ViewColumnType[st.Columns.Length-1]);
				ReloadTree(r);
			}
		}

		private void mnuConvertToXSD_Click(object sender, System.EventArgs e)
		{
			System.Xml.Schema.XmlSchema schema;
			try
			{
				dlgSaveXSD.ShowDialog();
			
				dv21_xsd.XSDGen xg= new XSDGen();


				xg.cd = this.cd;
				schema=xg.BuildSchema(); 
				//schema.Compile(null);
				
				System.Xml.XmlNamespaceManager nsmgr = new System.Xml.XmlNamespaceManager(new System.Xml.NameTable());
				nsmgr.AddNamespace("xs", dv21_xsd.XSDT.nsn);
			

				System.IO.FileStream fs = new System.IO.FileStream(dlgSaveXSD.FileName, System.IO.FileMode.Create);
			
				schema.Write( fs, nsmgr);
			}
			catch{}

		}

		

		private void mnuconst_CS_Click(object sender, System.EventArgs e)
		{
			string result;


			result = "public class " + cd.Alias  +"_CONST {\r\n";
			result +="public const string C_" + cd.Alias + "=\"" + cd.ID  +  "\";\r\n";
			if (cd.Sections !=null && cd.Sections.Length>0)
			{
				foreach(SectionType s in cd.Sections)
				{
					result +=ProcessSection(s,"CS");

				}
			}
			result +="\r\n}";
			Clipboard.SetDataObject(result,true);
			System.Windows.Forms.MessageBox.Show(this,"Константы загружены в буфер обмена","C#"); 
		}

		private string ProcessSection(dv21.SectionType  s, string dialect)
		{
			string result="";


			/////////////////////////// CS ///////////////////////////////
			if (dialect=="CS")
			{
				result +="public const string S_" + s.Alias + "=\"" + s.ID  +  "\";\r\n";
				if(s.Field !=null && s.Field.Length>0)
				{
					foreach(dv21.FieldType f in s.Field)
					{
						result +="public const string F_" + s.Alias + "_" + f.Alias  + "=\"" + f.ID  +  "\";\r\n";
					}

					result +="static public  string[] F_" + s.Alias + " {\r\n  get {\r\n return new string[] { \r\n";
					bool first=true;

					foreach(dv21.FieldType f in s.Field)
					{
						if (first==false) result += ",";

						result += "\"" + f.Alias  + "\"\r\n";
						first = false;
					}
					result +="\r\n}; }\r\n }\r\n";
				}

				if (s.Section !=null && s.Section.Length>0)
				{
					foreach(dv21.SectionType  z in s.Section)
					{
						result +=ProcessSection(z,"CS");
					}
				}

			}

			/////////////////////////// CS ///////////////////////////////
			if (dialect=="CPP")
			{
				result +="const string S_" + s.Alias + "=\"" + s.ID  +  "\";\r\n";
				if(s.Field !=null && s.Field.Length>0)
				{
					foreach(dv21.FieldType f in s.Field)
					{
						result +="const char* F_" + s.Alias + "_" + f.Alias  + "=\"" + f.ID  +  "\";\r\n";
					}

					result +="const  char*[]  F_" + s.Alias + "= {\r\n   ";
					bool first=true;

					foreach(dv21.FieldType f in s.Field)
					{
						if (first==false) result += ",";

						result += "\"" + f.Alias  + "\"\r\n";
						first = false;
					}
					result +="};\r\n";
				}

				if (s.Section !=null && s.Section.Length>0)
				{
					foreach(dv21.SectionType  z in s.Section)
					{
						result +=ProcessSection(z,"CPP");
					}
				}

			}

			////////////////////////// VB ////////////////////////
			if (dialect=="VB")
			{
				result +="public const S_" + s.Alias + " as string =\"" + s.ID  +  "\"\r\n";
				if(s.Field !=null && s.Field.Length>0)
				{
					foreach(dv21.FieldType f in s.Field)
					{
						result +="public const  F_" + s.Alias + "_" + f.Alias  + "_ID as string  =\"" + f.ID  +  "\"\r\n";
						result +="public const  F_" + s.Alias + "_" + f.Alias  + " as string  =\"" + f.Alias  +  "\"\r\n";
					}
				}
				if (s.Section !=null && s.Section.Length>0)
				{
					foreach(dv21.SectionType  z in s.Section)
					{
						result +=ProcessSection(z,"VB");
					}
				}
			}
			return result;
		}

		private void mnuConst_VB_Click(object sender, System.EventArgs e)
		{
			string result;


			result="";
			result +="public const C_" + cd.Alias + " as string =\"" + cd.ID  +  "\"\r\n";
			if (cd.Sections !=null && cd.Sections.Length>0)
			{
				foreach(SectionType s in cd.Sections)
				{
					result +=ProcessSection(s,"VB");

				}
			}
			Clipboard.SetDataObject(result,true);
			System.Windows.Forms.MessageBox.Show(this,"Константы загружены в буфер обмена","VB"); 
		}

		private void mnuConst_CPP_Click(object sender, System.EventArgs e)
		{
			string result;


			result = "public class " + cd.Alias  +"_CONST {\r\n";
			result +="public: \r\n const string C_" + cd.Alias + "=\"" + cd.ID  +  "\";\r\n";
			if (cd.Sections !=null && cd.Sections.Length>0)
			{
				foreach(SectionType s in cd.Sections)
				{
					result +=ProcessSection(s,"CPP");

				}
			}
			result +="\r\n}";
			Clipboard.SetDataObject(result,true);
			System.Windows.Forms.MessageBox.Show(this,"Константы загружены в буфер обмена","C++"); 		}

		private void mnuTypeLib_Click(object sender, System.EventArgs e)
		{
			dv21.DefFile  df=null;
			try
			{
				df = MyUtils.DeSerializeLib(Application.StartupPath + "\\lib.xml");
			}
			catch( System.Exception ex)
			{
				MessageBox.Show("Open library error: " + ex.Message);
			}

			if (df==null)
			{
				df = new DefFile ();
				df.Paths = new DefFilePaths[1];
				df.Paths[0] = new DefFilePaths();
				df.Paths[0].Path="c:\\";
			}	
			
			dv21_tl.TypeLibEditor t = new dv21_tl.TypeLibEditor();
			t.DefFilePaths = df.Paths;
			t.ShowDialog(); 
			df.Paths = t.DefFilePaths;
			MyUtils.SerializeObject(Application.StartupPath + "\\lib.xml",df);	
		}

		private void pnlModeType_Load(object sender, System.EventArgs e)
		{
		
		}

        private void mnuGenPG_Click(object sender, EventArgs e)
        {
			string sql;
            try
            {
                dlgSaveSQL.ShowDialog();
                dv21.PGGen pg = new PGGen();
                pg.cd = this.cd;
				sql = pg.Generate();
                System.IO.File.WriteAllText(dlgSaveSQL.FileName, sql);
                
            }
            catch { }
        }

        private void mnuAction_Popup(object sender, EventArgs e)
        {

        }

        private void mnuJDLGn_Click(object sender, EventArgs e)
        {

            string sql;
            try
            {
                dlgSaveJDL.ShowDialog();
                dv21.JDLGen pg = new JDLGen();
                pg.cd = this.cd;
                sql = pg.Generate();
                System.IO.File.WriteAllText(dlgSaveJDL.FileName, sql);

            }
            catch { }
        }

        private void dlgSaveSQL_FileOk(object sender, CancelEventArgs e)
        {

        }

        private void mnuFieldList_Click(object sender, EventArgs e)
        {
            string sql;
            try
            {
                dlgSaveCSV.ShowDialog();
                dv21.FieldList pg = new FieldList();
                pg.cd = this.cd;
                sql = pg.Generate();
                System.IO.File.WriteAllText(dlgSaveCSV.FileName, sql);

            }
            catch { }
        }

        private void menuItem6_Click(object sender, EventArgs e)
        {
            string sql;
            try
            {
                dlgSaveSQL.ShowDialog();
                dv21.PGGen pg = new PGGen();
                pg.cd = this.cd;
                sql = pg.GenerateAll();
                System.IO.File.WriteAllText(dlgSaveSQL.FileName, sql);

            }
            catch { }
        }

        private void mnuJDLGenAll_Click(object sender, EventArgs e)
        {
            string sql;
            try
            {
                dlgSaveJDL.ShowDialog();
                dv21.JDLGen pg = new JDLGen();
                pg.cd = this.cd;
                sql = pg.GenerateAll();
                System.IO.File.WriteAllText(dlgSaveJDL.FileName, sql);

            }
            catch { }
        }

        private void mnuFieldListAll_Click(object sender, EventArgs e)
        {
            string sql;
            try
            {
                dlgSaveCSV.ShowDialog();
                dv21.FieldList pg = new FieldList();
                pg.cd = this.cd;
                sql = pg.GenerateAll();
                System.IO.File.WriteAllText(dlgSaveCSV.FileName, sql);

            }
            catch { }
        }

        private void mnuJDL_i18n_Click(object sender, EventArgs e)
        {
			dlgFolder.ShowNewFolderButton = true;

            if (dlgFolder.ShowDialog()== DialogResult.OK)
			{
				string path = dlgFolder.SelectedPath;

                
                    dv21.JDLGen jdl = new JDLGen();
					jdl.cd = this.cd;
					jdl.GenerateAlli18n(  "fullApp", path);
                    

                
            }
        }
    }




}