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
            this.fontDialog1 = new System.Windows.Forms.FontDialog();
            this.fontDialog2 = new System.Windows.Forms.FontDialog();
            this.dgDest = new System.Windows.Forms.DataGridView();
            this.dgSrc = new System.Windows.Forms.DataGridView();
            this.cmdSaveLink = new System.Windows.Forms.Button();
            this.txtFilter = new System.Windows.Forms.TextBox();
            this.txtComment = new System.Windows.Forms.TextBox();
            this.cmdDelLink = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.cmdFind = new System.Windows.Forms.Button();
            this.cmdFindDest = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.txtFindDest = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.cmdRefresh = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dgDest)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgSrc)).BeginInit();
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
            this.dgDest.Location = new System.Drawing.Point(22, 114);
            this.dgDest.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dgDest.Name = "dgDest";
            this.dgDest.ReadOnly = true;
            this.dgDest.RowHeadersVisible = false;
            this.dgDest.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgDest.ShowEditingIcon = false;
            this.dgDest.Size = new System.Drawing.Size(692, 567);
            this.dgDest.TabIndex = 0;
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
            this.dgSrc.Location = new System.Drawing.Point(722, 114);
            this.dgSrc.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dgSrc.Name = "dgSrc";
            this.dgSrc.ReadOnly = true;
            this.dgSrc.RowHeadersVisible = false;
            this.dgSrc.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgSrc.ShowEditingIcon = false;
            this.dgSrc.Size = new System.Drawing.Size(677, 567);
            this.dgSrc.TabIndex = 1;
            this.dgSrc.CellFormatting += new System.Windows.Forms.DataGridViewCellFormattingEventHandler(this.dgSrc_CellFormatting);
            this.dgSrc.SelectionChanged += new System.EventHandler(this.dgSrc_SelectionChanged);
            // 
            // cmdSaveLink
            // 
            this.cmdSaveLink.Font = new System.Drawing.Font("Segoe UI Semibold", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.cmdSaveLink.ForeColor = System.Drawing.Color.Green;
            this.cmdSaveLink.Location = new System.Drawing.Point(668, 4);
            this.cmdSaveLink.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.cmdSaveLink.Name = "cmdSaveLink";
            this.cmdSaveLink.Size = new System.Drawing.Size(218, 30);
            this.cmdSaveLink.TabIndex = 2;
            this.cmdSaveLink.Text = "Save Link";
            this.cmdSaveLink.UseVisualStyleBackColor = true;
            this.cmdSaveLink.Click += new System.EventHandler(this.cmdSaveLink_Click);
            // 
            // txtFilter
            // 
            this.txtFilter.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.txtFilter.Location = new System.Drawing.Point(1122, 74);
            this.txtFilter.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtFilter.Name = "txtFilter";
            this.txtFilter.Size = new System.Drawing.Size(231, 23);
            this.txtFilter.TabIndex = 3;
            this.txtFilter.TextChanged += new System.EventHandler(this.txtFilter_TextChanged);
            // 
            // txtComment
            // 
            this.txtComment.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtComment.Location = new System.Drawing.Point(380, 37);
            this.txtComment.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtComment.Multiline = true;
            this.txtComment.Name = "txtComment";
            this.txtComment.Size = new System.Drawing.Size(725, 60);
            this.txtComment.TabIndex = 4;
            // 
            // cmdDelLink
            // 
            this.cmdDelLink.Font = new System.Drawing.Font("Segoe UI Semibold", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.cmdDelLink.ForeColor = System.Drawing.Color.Red;
            this.cmdDelLink.Location = new System.Drawing.Point(894, 4);
            this.cmdDelLink.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.cmdDelLink.Name = "cmdDelLink";
            this.cmdDelLink.Size = new System.Drawing.Size(211, 30);
            this.cmdDelLink.TabIndex = 6;
            this.cmdDelLink.Text = "Delete Link";
            this.cmdDelLink.UseVisualStyleBackColor = true;
            this.cmdDelLink.Click += new System.EventHandler(this.cmdDelLink_Click);
            // 
            // label1
            // 
            this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(1302, 44);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(97, 15);
            this.label1.TabIndex = 7;
            this.label1.Text = "Find source table";
            // 
            // cmdFind
            // 
            this.cmdFind.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.cmdFind.Location = new System.Drawing.Point(1361, 69);
            this.cmdFind.Name = "cmdFind";
            this.cmdFind.Size = new System.Drawing.Size(39, 28);
            this.cmdFind.TabIndex = 8;
            this.cmdFind.Text = "!";
            this.cmdFind.UseVisualStyleBackColor = true;
            this.cmdFind.Click += new System.EventHandler(this.cmdFind_Click);
            // 
            // cmdFindDest
            // 
            this.cmdFindDest.Location = new System.Drawing.Point(23, 70);
            this.cmdFindDest.Name = "cmdFindDest";
            this.cmdFindDest.Size = new System.Drawing.Size(39, 28);
            this.cmdFindDest.TabIndex = 11;
            this.cmdFindDest.Text = "!";
            this.cmdFindDest.UseVisualStyleBackColor = true;
            this.cmdFindDest.Click += new System.EventHandler(this.cmdFindDest_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(23, 44);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(121, 15);
            this.label2.TabIndex = 10;
            this.label2.Text = "Find destination table";
            // 
            // txtFindDest
            // 
            this.txtFindDest.Location = new System.Drawing.Point(69, 74);
            this.txtFindDest.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtFindDest.Name = "txtFindDest";
            this.txtFindDest.Size = new System.Drawing.Size(294, 23);
            this.txtFindDest.TabIndex = 9;
            this.txtFindDest.TextChanged += new System.EventHandler(this.txtFindDest_TextChanged);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(380, 9);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(84, 15);
            this.label3.TabIndex = 12;
            this.label3.Text = "Link comment";
            // 
            // cmdRefresh
            // 
            this.cmdRefresh.Location = new System.Drawing.Point(23, 4);
            this.cmdRefresh.Name = "cmdRefresh";
            this.cmdRefresh.Size = new System.Drawing.Size(170, 27);
            this.cmdRefresh.TabIndex = 13;
            this.cmdRefresh.Text = "Refresh";
            this.cmdRefresh.UseVisualStyleBackColor = true;
            this.cmdRefresh.Click += new System.EventHandler(this.cmdRefresh_Click);
            // 
            // frmMapper
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1412, 703);
            this.Controls.Add(this.cmdRefresh);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.cmdFindDest);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.txtFindDest);
            this.Controls.Add(this.cmdFind);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.cmdDelLink);
            this.Controls.Add(this.txtComment);
            this.Controls.Add(this.txtFilter);
            this.Controls.Add(this.cmdSaveLink);
            this.Controls.Add(this.dgSrc);
            this.Controls.Add(this.dgDest);
            this.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.Name = "frmMapper";
            this.Text = "Отображение полей";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.frmMapper_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.dgDest)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgSrc)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.FontDialog fontDialog1;
        private System.Windows.Forms.FontDialog fontDialog2;
        private System.Windows.Forms.DataGridView dgDest;
        private System.Windows.Forms.DataGridView dgSrc;
        private System.Windows.Forms.Button cmdSaveLink;
        private System.Windows.Forms.TextBox txtFilter;
        private System.Windows.Forms.TextBox txtComment;
        private Button cmdDelLink;
        private Label label1;
        private Button cmdFind;
        private Button cmdFindDest;
        private Label label2;
        private TextBox txtFindDest;
        private Label label3;
        private Button cmdRefresh;
    }
}