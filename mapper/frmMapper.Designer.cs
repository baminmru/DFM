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
            fontDialog1 = new FontDialog();
            fontDialog2 = new FontDialog();
            dgDest = new DataGridView();
            dgSrc = new DataGridView();
            cmdSaveLink = new Button();
            txtFilter = new TextBox();
            txtComment = new TextBox();
            cmdDelLink = new Button();
            label1 = new Label();
            cmdFind = new Button();
            ((System.ComponentModel.ISupportInitialize)dgDest).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dgSrc).BeginInit();
            SuspendLayout();
            // 
            // dgDest
            // 
            dgDest.AllowUserToAddRows = false;
            dgDest.AllowUserToDeleteRows = false;
            dgDest.AllowUserToOrderColumns = true;
            dgDest.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left;
            dgDest.ClipboardCopyMode = DataGridViewClipboardCopyMode.Disable;
            dgDest.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dgDest.EditMode = DataGridViewEditMode.EditProgrammatically;
            dgDest.Location = new Point(22, 23);
            dgDest.Margin = new Padding(4, 3, 4, 3);
            dgDest.Name = "dgDest";
            dgDest.ReadOnly = true;
            dgDest.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgDest.Size = new Size(400, 658);
            dgDest.TabIndex = 0;
            dgDest.CellContentClick += dgDest_CellContentClick;
            dgDest.SelectionChanged += dgDest_SelectionChanged;
            // 
            // dgSrc
            // 
            dgSrc.AllowUserToAddRows = false;
            dgSrc.AllowUserToDeleteRows = false;
            dgSrc.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            dgSrc.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dgSrc.EditMode = DataGridViewEditMode.EditProgrammatically;
            dgSrc.Location = new Point(948, 93);
            dgSrc.Margin = new Padding(4, 3, 4, 3);
            dgSrc.Name = "dgSrc";
            dgSrc.ReadOnly = true;
            dgSrc.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgSrc.Size = new Size(451, 588);
            dgSrc.TabIndex = 1;
            dgSrc.SelectionChanged += dgSrc_SelectionChanged;
            // 
            // cmdSaveLink
            // 
            cmdSaveLink.Location = new Point(445, 23);
            cmdSaveLink.Margin = new Padding(4, 3, 4, 3);
            cmdSaveLink.Name = "cmdSaveLink";
            cmdSaveLink.Size = new Size(257, 48);
            cmdSaveLink.TabIndex = 2;
            cmdSaveLink.Text = "Save Link";
            cmdSaveLink.UseVisualStyleBackColor = true;
            cmdSaveLink.Click += cmdSaveLink_Click;
            // 
            // txtFilter
            // 
            txtFilter.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;
            txtFilter.Location = new Point(1004, 48);
            txtFilter.Margin = new Padding(4, 3, 4, 3);
            txtFilter.Name = "txtFilter";
            txtFilter.Size = new Size(395, 23);
            txtFilter.TabIndex = 3;
            txtFilter.TextChanged += txtFilter_TextChanged;
            // 
            // txtComment
            // 
            txtComment.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left;
            txtComment.Location = new Point(445, 86);
            txtComment.Margin = new Padding(4, 3, 4, 3);
            txtComment.Multiline = true;
            txtComment.Name = "txtComment";
            txtComment.Size = new Size(485, 595);
            txtComment.TabIndex = 4;
            // 
            // cmdDelLink
            // 
            cmdDelLink.Location = new Point(709, 23);
            cmdDelLink.Margin = new Padding(4, 3, 4, 3);
            cmdDelLink.Name = "cmdDelLink";
            cmdDelLink.Size = new Size(221, 48);
            cmdDelLink.TabIndex = 6;
            cmdDelLink.Text = "Delete Link";
            cmdDelLink.UseVisualStyleBackColor = true;
            cmdDelLink.Click += cmdDelLink_Click;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(948, 16);
            label1.Name = "label1";
            label1.Size = new Size(30, 15);
            label1.TabIndex = 7;
            label1.Text = "Find";
            // 
            // cmdFind
            // 
            cmdFind.Location = new Point(951, 43);
            cmdFind.Name = "cmdFind";
            cmdFind.Size = new Size(39, 28);
            cmdFind.TabIndex = 8;
            cmdFind.Text = "!";
            cmdFind.UseVisualStyleBackColor = true;
            cmdFind.Click += cmdFind_Click;
            // 
            // frmMapper
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1412, 703);
            Controls.Add(cmdFind);
            Controls.Add(label1);
            Controls.Add(cmdDelLink);
            Controls.Add(txtComment);
            Controls.Add(txtFilter);
            Controls.Add(cmdSaveLink);
            Controls.Add(dgSrc);
            Controls.Add(dgDest);
            Margin = new Padding(4, 3, 4, 3);
            Name = "frmMapper";
            Text = "frmMapper";
            FormClosed += frmMapper_FormClosed;
            Load += frmMapper_Load;
            ((System.ComponentModel.ISupportInitialize)dgDest).EndInit();
            ((System.ComponentModel.ISupportInitialize)dgSrc).EndInit();
            ResumeLayout(false);
            PerformLayout();

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