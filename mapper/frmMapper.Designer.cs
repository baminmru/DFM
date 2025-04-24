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
            cmdFindDest = new Button();
            label2 = new Label();
            txtFindDest = new TextBox();
            label3 = new Label();
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
            dgDest.Location = new Point(22, 86);
            dgDest.Margin = new Padding(4, 3, 4, 3);
            dgDest.Name = "dgDest";
            dgDest.ReadOnly = true;
            dgDest.RowHeadersVisible = false;
            dgDest.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgDest.ShowEditingIcon = false;
            dgDest.Size = new Size(559, 595);
            dgDest.TabIndex = 0;
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
            dgSrc.RowHeadersVisible = false;
            dgSrc.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgSrc.ShowEditingIcon = false;
            dgSrc.Size = new Size(451, 588);
            dgSrc.TabIndex = 1;
            dgSrc.SelectionChanged += dgSrc_SelectionChanged;
            // 
            // cmdSaveLink
            // 
            cmdSaveLink.Font = new Font("Segoe UI Semibold", 12F, FontStyle.Bold, GraphicsUnit.Point);
            cmdSaveLink.ForeColor = Color.Green;
            cmdSaveLink.Location = new Point(589, 23);
            cmdSaveLink.Margin = new Padding(4, 3, 4, 3);
            cmdSaveLink.Name = "cmdSaveLink";
            cmdSaveLink.Size = new Size(167, 48);
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
            txtComment.Location = new Point(589, 103);
            txtComment.Margin = new Padding(4, 3, 4, 3);
            txtComment.Multiline = true;
            txtComment.Name = "txtComment";
            txtComment.Size = new Size(351, 578);
            txtComment.TabIndex = 4;
            // 
            // cmdDelLink
            // 
            cmdDelLink.Font = new Font("Segoe UI Semibold", 12F, FontStyle.Bold, GraphicsUnit.Point);
            cmdDelLink.ForeColor = Color.Red;
            cmdDelLink.Location = new Point(764, 23);
            cmdDelLink.Margin = new Padding(4, 3, 4, 3);
            cmdDelLink.Name = "cmdDelLink";
            cmdDelLink.Size = new Size(166, 48);
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
            label1.Size = new Size(97, 15);
            label1.TabIndex = 7;
            label1.Text = "Find source table";
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
            // cmdFindDest
            // 
            cmdFindDest.Location = new Point(23, 43);
            cmdFindDest.Name = "cmdFindDest";
            cmdFindDest.Size = new Size(39, 28);
            cmdFindDest.TabIndex = 11;
            cmdFindDest.Text = "!";
            cmdFindDest.UseVisualStyleBackColor = true;
            cmdFindDest.Click += cmdFindDest_Click;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(20, 16);
            label2.Name = "label2";
            label2.Size = new Size(121, 15);
            label2.TabIndex = 10;
            label2.Text = "Find destination table";
            // 
            // txtFindDest
            // 
            txtFindDest.Location = new Point(76, 48);
            txtFindDest.Margin = new Padding(4, 3, 4, 3);
            txtFindDest.Name = "txtFindDest";
            txtFindDest.Size = new Size(505, 23);
            txtFindDest.TabIndex = 9;
            txtFindDest.TextChanged += txtFindDest_TextChanged;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(597, 85);
            label3.Name = "label3";
            label3.Size = new Size(84, 15);
            label3.TabIndex = 12;
            label3.Text = "Link comment";
            // 
            // frmMapper
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1412, 703);
            Controls.Add(label3);
            Controls.Add(cmdFindDest);
            Controls.Add(label2);
            Controls.Add(txtFindDest);
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
            Text = "Отображение полей";
            FormClosed += frmMapper_FormClosed;
            Load += frmMapper_Load_1;
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
        private Button cmdFindDest;
        private Label label2;
        private TextBox txtFindDest;
        private Label label3;
    }
}