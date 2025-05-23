namespace mapper
{
    partial class frmMapper
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.dgDest = new System.Windows.Forms.DataGridView();
            this.dgSrc = new System.Windows.Forms.DataGridView();
            this.cmdSaveLink = new System.Windows.Forms.Button();
            this.txtFilter = new System.Windows.Forms.TextBox();
            this.txtComment = new System.Windows.Forms.TextBox();
            this.cmdDelLink = new System.Windows.Forms.Button();
            this.cmdFind = new System.Windows.Forms.Button();
            this.cmdFindDest = new System.Windows.Forms.Button();
            this.txtFindDest = new System.Windows.Forms.TextBox();
            this.dlgSave = new System.Windows.Forms.SaveFileDialog();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.generatePtablesToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.mnuGenViews = new System.Windows.Forms.ToolStripMenuItem();
            this.mnuBuildMap = new System.Windows.Forms.ToolStripMenuItem();
            this.mnuRefresh = new System.Windows.Forms.ToolStripMenuItem();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.cmbMapName = new System.Windows.Forms.ComboBox();
            this.cmbAPI = new System.Windows.Forms.ComboBox();
            this.txtAPIMask = new System.Windows.Forms.TextBox();
            this.cmdToUsed = new System.Windows.Forms.Button();
            this.chkUsedOnly = new System.Windows.Forms.CheckBox();
            this.txtCondition = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            ((System.ComponentModel.ISupportInitialize)(this.dgDest)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgSrc)).BeginInit();
            this.menuStrip1.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.SuspendLayout();
            // 
            // dgDest
            // 
            this.dgDest.AllowUserToAddRows = false;
            this.dgDest.AllowUserToDeleteRows = false;
            this.dgDest.AllowUserToOrderColumns = true;
            this.dgDest.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.dgDest.ClipboardCopyMode = System.Windows.Forms.DataGridViewClipboardCopyMode.Disable;
            this.dgDest.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgDest.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dgDest.Location = new System.Drawing.Point(10, 162);
            this.dgDest.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dgDest.Name = "dgDest";
            this.dgDest.ReadOnly = true;
            this.dgDest.RowHeadersVisible = false;
            this.dgDest.RowHeadersWidth = 51;
            this.dgDest.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgDest.ShowEditingIcon = false;
            this.dgDest.Size = new System.Drawing.Size(695, 529);
            this.dgDest.TabIndex = 0;
            this.dgDest.CellMouseDoubleClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dgDest_CellMouseDoubleClick);
            this.dgDest.SelectionChanged += new System.EventHandler(this.dgDest_SelectionChanged);
            // 
            // dgSrc
            // 
            this.dgSrc.AllowUserToAddRows = false;
            this.dgSrc.AllowUserToDeleteRows = false;
            this.dgSrc.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.dgSrc.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgSrc.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dgSrc.Location = new System.Drawing.Point(722, 162);
            this.dgSrc.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dgSrc.Name = "dgSrc";
            this.dgSrc.ReadOnly = true;
            this.dgSrc.RowHeadersVisible = false;
            this.dgSrc.RowHeadersWidth = 51;
            this.dgSrc.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgSrc.ShowEditingIcon = false;
            this.dgSrc.Size = new System.Drawing.Size(667, 529);
            this.dgSrc.TabIndex = 1;
            this.dgSrc.CellFormatting += new System.Windows.Forms.DataGridViewCellFormattingEventHandler(this.dgSrc_CellFormatting);
            this.dgSrc.CellMouseDoubleClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dgSrc_CellMouseDoubleClick);
            this.dgSrc.SelectionChanged += new System.EventHandler(this.dgSrc_SelectionChanged);
            // 
            // cmdSaveLink
            // 
            this.cmdSaveLink.Font = new System.Drawing.Font("Segoe UI Semibold", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.cmdSaveLink.ForeColor = System.Drawing.Color.Green;
            this.cmdSaveLink.Location = new System.Drawing.Point(472, 22);
            this.cmdSaveLink.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.cmdSaveLink.Name = "cmdSaveLink";
            this.cmdSaveLink.Size = new System.Drawing.Size(114, 30);
            this.cmdSaveLink.TabIndex = 2;
            this.cmdSaveLink.Text = "Save Link";
            this.cmdSaveLink.UseVisualStyleBackColor = true;
            this.cmdSaveLink.Click += new System.EventHandler(this.cmdSaveLink_Click);
            // 
            // txtFilter
            // 
            this.txtFilter.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtFilter.Location = new System.Drawing.Point(7, 97);
            this.txtFilter.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtFilter.Name = "txtFilter";
            this.txtFilter.PlaceholderText = "Table prefix for search";
            this.txtFilter.Size = new System.Drawing.Size(217, 23);
            this.txtFilter.TabIndex = 3;
            this.toolTip1.SetToolTip(this.txtFilter, "Table prefix");
            this.txtFilter.TextChanged += new System.EventHandler(this.txtFilter_TextChanged);
            // 
            // txtComment
            // 
            this.txtComment.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtComment.Location = new System.Drawing.Point(7, 93);
            this.txtComment.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtComment.Name = "txtComment";
            this.txtComment.PlaceholderText = "Link comment";
            this.txtComment.Size = new System.Drawing.Size(719, 23);
            this.txtComment.TabIndex = 4;
            this.toolTip1.SetToolTip(this.txtComment, "Link comment");
            // 
            // cmdDelLink
            // 
            this.cmdDelLink.Font = new System.Drawing.Font("Segoe UI Semibold", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.cmdDelLink.ForeColor = System.Drawing.Color.Red;
            this.cmdDelLink.Location = new System.Drawing.Point(605, 22);
            this.cmdDelLink.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.cmdDelLink.Name = "cmdDelLink";
            this.cmdDelLink.Size = new System.Drawing.Size(121, 30);
            this.cmdDelLink.TabIndex = 6;
            this.cmdDelLink.Text = "Delete Link";
            this.cmdDelLink.UseVisualStyleBackColor = true;
            this.cmdDelLink.Click += new System.EventHandler(this.cmdDelLink_Click);
            // 
            // cmdFind
            // 
            this.cmdFind.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.cmdFind.Location = new System.Drawing.Point(228, 93);
            this.cmdFind.Name = "cmdFind";
            this.cmdFind.Size = new System.Drawing.Size(39, 28);
            this.cmdFind.TabIndex = 8;
            this.cmdFind.Text = "!";
            this.toolTip1.SetToolTip(this.cmdFind, "Find at Source API");
            this.cmdFind.UseVisualStyleBackColor = true;
            this.cmdFind.Click += new System.EventHandler(this.cmdFind_Click);
            // 
            // cmdFindDest
            // 
            this.cmdFindDest.Location = new System.Drawing.Point(313, 48);
            this.cmdFindDest.Name = "cmdFindDest";
            this.cmdFindDest.Size = new System.Drawing.Size(39, 28);
            this.cmdFindDest.TabIndex = 11;
            this.cmdFindDest.Text = "!";
            this.cmdFindDest.UseVisualStyleBackColor = true;
            this.cmdFindDest.Click += new System.EventHandler(this.cmdFindDest_Click);
            // 
            // txtFindDest
            // 
            this.txtFindDest.Location = new System.Drawing.Point(22, 52);
            this.txtFindDest.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtFindDest.Name = "txtFindDest";
            this.txtFindDest.PlaceholderText = "Table prefix for search";
            this.txtFindDest.Size = new System.Drawing.Size(275, 23);
            this.txtFindDest.TabIndex = 9;
            this.toolTip1.SetToolTip(this.txtFindDest, "table prefix");
            this.txtFindDest.TextChanged += new System.EventHandler(this.txtFindDest_TextChanged);
            // 
            // menuStrip1
            // 
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.generatePtablesToolStripMenuItem,
            this.mnuGenViews,
            this.mnuBuildMap,
            this.mnuRefresh});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(1412, 24);
            this.menuStrip1.TabIndex = 14;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // generatePtablesToolStripMenuItem
            // 
            this.generatePtablesToolStripMenuItem.Name = "generatePtablesToolStripMenuItem";
            this.generatePtablesToolStripMenuItem.Size = new System.Drawing.Size(112, 20);
            this.generatePtablesToolStripMenuItem.Text = "Generate p-tables";
            this.generatePtablesToolStripMenuItem.Click += new System.EventHandler(this.generatePtablesToolStripMenuItem_Click);
            // 
            // mnuGenViews
            // 
            this.mnuGenViews.Name = "mnuGenViews";
            this.mnuGenViews.Size = new System.Drawing.Size(98, 20);
            this.mnuGenViews.Text = "Generate views";
            this.mnuGenViews.Click += new System.EventHandler(this.mnuGenViews_Click);
            // 
            // mnuBuildMap
            // 
            this.mnuBuildMap.Name = "mnuBuildMap";
            this.mnuBuildMap.Size = new System.Drawing.Size(105, 20);
            this.mnuBuildMap.Text = "Build Map script";
            this.mnuBuildMap.Click += new System.EventHandler(this.mnuBuildMap_Click);
            // 
            // mnuRefresh
            // 
            this.mnuRefresh.Name = "mnuRefresh";
            this.mnuRefresh.Size = new System.Drawing.Size(58, 20);
            this.mnuRefresh.Text = "Refresh";
            this.mnuRefresh.Click += new System.EventHandler(this.mnuRefresh_Click);
            // 
            // cmbMapName
            // 
            this.cmbMapName.FormattingEnabled = true;
            this.cmbMapName.Location = new System.Drawing.Point(7, 22);
            this.cmbMapName.Name = "cmbMapName";
            this.cmbMapName.Size = new System.Drawing.Size(444, 23);
            this.cmbMapName.TabIndex = 15;
            this.toolTip1.SetToolTip(this.cmbMapName, "Map Name");
            this.cmbMapName.TextChanged += new System.EventHandler(this.cmbMapName_TextChanged);
            // 
            // cmbAPI
            // 
            this.cmbAPI.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.cmbAPI.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbAPI.FormattingEnabled = true;
            this.cmbAPI.Location = new System.Drawing.Point(6, 64);
            this.cmbAPI.Name = "cmbAPI";
            this.cmbAPI.Size = new System.Drawing.Size(218, 23);
            this.cmbAPI.TabIndex = 18;
            this.toolTip1.SetToolTip(this.cmbAPI, "scroll to selected API ");
            this.cmbAPI.SelectedIndexChanged += new System.EventHandler(this.cmbAPI_SelectedIndexChanged);
            // 
            // txtAPIMask
            // 
            this.txtAPIMask.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtAPIMask.Location = new System.Drawing.Point(6, 35);
            this.txtAPIMask.Name = "txtAPIMask";
            this.txtAPIMask.PlaceholderText = "Api pattern ( sql syntax) ";
            this.txtAPIMask.Size = new System.Drawing.Size(261, 23);
            this.txtAPIMask.TabIndex = 19;
            this.toolTip1.SetToolTip(this.txtAPIMask, "API mask");
            this.txtAPIMask.TextChanged += new System.EventHandler(this.textAPIMAsk_TextChanged);
            // 
            // cmdToUsed
            // 
            this.cmdToUsed.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.cmdToUsed.Location = new System.Drawing.Point(229, 64);
            this.cmdToUsed.Name = "cmdToUsed";
            this.cmdToUsed.Size = new System.Drawing.Size(37, 24);
            this.cmdToUsed.TabIndex = 21;
            this.cmdToUsed.Text = "2U";
            this.toolTip1.SetToolTip(this.cmdToUsed, "Add api to Used_API ");
            this.cmdToUsed.UseVisualStyleBackColor = true;
            this.cmdToUsed.Click += new System.EventHandler(this.cmdToUsed_Click);
            // 
            // chkUsedOnly
            // 
            this.chkUsedOnly.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.chkUsedOnly.AutoSize = true;
            this.chkUsedOnly.Location = new System.Drawing.Point(168, 10);
            this.chkUsedOnly.Name = "chkUsedOnly";
            this.chkUsedOnly.Size = new System.Drawing.Size(99, 19);
            this.chkUsedOnly.TabIndex = 20;
            this.chkUsedOnly.Text = "Used API only";
            this.toolTip1.SetToolTip(this.chkUsedOnly, "Find from used API only");
            this.chkUsedOnly.UseVisualStyleBackColor = true;
            this.chkUsedOnly.CheckedChanged += new System.EventHandler(this.chkUsedOnly_CheckedChanged);
            // 
            // txtCondition
            // 
            this.txtCondition.Location = new System.Drawing.Point(7, 60);
            this.txtCondition.Name = "txtCondition";
            this.txtCondition.PlaceholderText = "Link condition";
            this.txtCondition.Size = new System.Drawing.Size(719, 23);
            this.txtCondition.TabIndex = 16;
            this.toolTip1.SetToolTip(this.txtCondition, "Link condition");
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txtCondition);
            this.groupBox1.Controls.Add(this.cmbMapName);
            this.groupBox1.Controls.Add(this.cmdDelLink);
            this.groupBox1.Controls.Add(this.txtComment);
            this.groupBox1.Controls.Add(this.cmdSaveLink);
            this.groupBox1.Location = new System.Drawing.Point(382, 27);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(733, 129);
            this.groupBox1.TabIndex = 17;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Mapping";
            // 
            // groupBox2
            // 
            this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox2.Controls.Add(this.cmdToUsed);
            this.groupBox2.Controls.Add(this.chkUsedOnly);
            this.groupBox2.Controls.Add(this.txtAPIMask);
            this.groupBox2.Controls.Add(this.cmbAPI);
            this.groupBox2.Controls.Add(this.cmdFind);
            this.groupBox2.Controls.Add(this.txtFilter);
            this.groupBox2.Location = new System.Drawing.Point(1116, 27);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(273, 134);
            this.groupBox2.TabIndex = 20;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Find source api \\  table";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.cmdFindDest);
            this.groupBox3.Controls.Add(this.txtFindDest);
            this.groupBox3.Location = new System.Drawing.Point(10, 33);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(367, 123);
            this.groupBox3.TabIndex = 21;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Find target";
            // 
            // frmMapper
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1412, 703);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.dgSrc);
            this.Controls.Add(this.dgDest);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.Name = "frmMapper";
            this.Text = "Отображение полей";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.frmMapper_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.dgDest)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgSrc)).EndInit();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.DataGridView dgDest;
        private System.Windows.Forms.DataGridView dgSrc;
        private System.Windows.Forms.Button cmdSaveLink;
        private System.Windows.Forms.TextBox txtFilter;
        private System.Windows.Forms.TextBox txtComment;
        private Button cmdDelLink;
        private Button cmdFind;
        private Button cmdFindDest;
        private TextBox txtFindDest;
        private SaveFileDialog dlgSave;
        private MenuStrip menuStrip1;
        private ToolStripMenuItem generatePtablesToolStripMenuItem;
        private ToolStripMenuItem mnuGenViews;
        private ToolTip toolTip1;
        private ComboBox cmbMapName;
        private GroupBox groupBox1;
        private ToolStripMenuItem mnuRefresh;
        private ComboBox cmbAPI;
        private TextBox txtAPIMask;
        private GroupBox groupBox2;
        private GroupBox groupBox3;
        private CheckBox chkUsedOnly;
        private Button cmdToUsed;
        private ToolStripMenuItem mnuBuildMap;
        private TextBox txtCondition;
    }
}