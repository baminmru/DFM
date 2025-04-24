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
            this.dgDest.Location = new System.Drawing.Point(22, 23);
            this.dgDest.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dgDest.Name = "dgDest";
            this.dgDest.ReadOnly = true;
            this.dgDest.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgDest.Size = new System.Drawing.Size(400, 658);
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
            this.dgSrc.Location = new System.Drawing.Point(948, 93);
            this.dgSrc.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dgSrc.Name = "dgSrc";
            this.dgSrc.ReadOnly = true;
            this.dgSrc.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgSrc.Size = new System.Drawing.Size(451, 588);
            this.dgSrc.TabIndex = 1;
            this.dgSrc.SelectionChanged += new System.EventHandler(this.dgSrc_SelectionChanged);
            // 
            // cmdSaveLink
            // 
            this.cmdSaveLink.Location = new System.Drawing.Point(445, 23);
            this.cmdSaveLink.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.cmdSaveLink.Name = "cmdSaveLink";
            this.cmdSaveLink.Size = new System.Drawing.Size(257, 48);
            this.cmdSaveLink.TabIndex = 2;
            this.cmdSaveLink.Text = "Save Link";
            this.cmdSaveLink.UseVisualStyleBackColor = true;
            this.cmdSaveLink.Click += new System.EventHandler(this.cmdSaveLink_Click);
            // 
            // txtFilter
            // 
            this.txtFilter.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtFilter.Location = new System.Drawing.Point(1004, 48);
            this.txtFilter.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtFilter.Name = "txtFilter";
            this.txtFilter.Size = new System.Drawing.Size(395, 23);
            this.txtFilter.TabIndex = 3;
            this.txtFilter.TextChanged += new System.EventHandler(this.txtFilter_TextChanged);
            // 
            // txtComment
            // 
            this.txtComment.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.txtComment.Location = new System.Drawing.Point(445, 86);
            this.txtComment.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txtComment.Multiline = true;
            this.txtComment.Name = "txtComment";
            this.txtComment.Size = new System.Drawing.Size(485, 595);
            this.txtComment.TabIndex = 4;
            // 
            // cmdDelLink
            // 
            this.cmdDelLink.Location = new System.Drawing.Point(709, 23);
            this.cmdDelLink.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.cmdDelLink.Name = "cmdDelLink";
            this.cmdDelLink.Size = new System.Drawing.Size(221, 48);
            this.cmdDelLink.TabIndex = 6;
            this.cmdDelLink.Text = "Delete Link";
            this.cmdDelLink.UseVisualStyleBackColor = true;
            this.cmdDelLink.Click += new System.EventHandler(this.cmdDelLink_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(948, 16);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(30, 15);
            this.label1.TabIndex = 7;
            this.label1.Text = "Find";
            // 
            // cmdFind
            // 
            this.cmdFind.Location = new System.Drawing.Point(951, 43);
            this.cmdFind.Name = "cmdFind";
            this.cmdFind.Size = new System.Drawing.Size(39, 28);
            this.cmdFind.TabIndex = 8;
            this.cmdFind.Text = "!";
            this.cmdFind.UseVisualStyleBackColor = true;
            this.cmdFind.Click += new System.EventHandler(this.cmdFind_Click);
            // 
            // frmMapper
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1412, 703);
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
    }
}