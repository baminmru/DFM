using System;
using System.Buffers;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace mapper
{
    public partial class frmMapper : Form
    {

        public pgDataSource DS;



        public frmMapper()
        {
            InitializeComponent();
        }

        private void frmMapper_Load(object sender, EventArgs e)
        {

        }

        public void Init()
        {

            DataTable dst = DS.ReadData("select * from dest_data order by table_name, field_name");
            dgDest.DataSource = dst;
            dgDest.ClearSelection();

            DataTable src = DS.ReadData("select * from src_data order by table_name, field_name");
            dgSrc.DataSource = src;
            dgSrc.ClearSelection();


        }

        private void dgDest_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }


        private int SelectedDST { get; set; }
        private int SelectedSRC { get; set; }

        private void dgDest_SelectionChanged(object sender, EventArgs e)
        {
            if (dgDest.SelectedRows.Count == 1)
            {
                DataGridViewRow row = this.dgDest.SelectedRows[0];
                SelectedDST = (int)row.Cells["id"].Value;
                DataTable map = DS.ReadData("select * from mapper where dest_id = " + SelectedDST.ToString());
                if (map.Rows.Count > 0)
                {
                    SelectedSRC = (int)map.Rows[0]["src_id"];
                    dgSrc.ClearSelection();

                    foreach (DataGridViewRow srow in dgSrc.Rows)
                    {
                        try
                        {
                            if (!(srow.Cells is null) && !(srow.Cells[4] is null))
                            {
                                if (srow.Cells[4].Value.ToString().Equals(SelectedSRC.ToString()))
                                {
                                    dgSrc.Rows[srow.Index].Selected = true;
                                    if (!dgSrc.Rows[srow.Index].Visible)
                                        dgSrc.FirstDisplayedScrollingRowIndex = srow.Index;
                                    txtComment.Text = map.Rows[0]["comment"].ToString();
                                    break;
                                }
                            }
                        }
                        catch { }
                    }




                }
                else
                {
                    txtComment.Text = "";
                    dgSrc.ClearSelection();
                }


            }
        }

        private void cmdSaveLink_Click(object sender, EventArgs e)
        {
            if (dgSrc.SelectedRows.Count == 1 && dgDest.SelectedRows.Count == 1)
            {
                DS.Execute("delete from  mapper where dest_id= " + SelectedDST.ToString());
                DS.Execute("insert into mapper(src_id,dest_id,comment) values(" + SelectedSRC.ToString() + "," + SelectedDST.ToString() + ",'" + txtComment.Text + "')");
            }
        }

        private void cmdDelLink_Click(object sender, EventArgs e)
        {
            if (dgDest.SelectedRows.Count == 1)
            {
                DS.Execute("delete from  mapper where dest_id= " + SelectedDST.ToString());
                dgSrc.ClearSelection();
            }
        }

        private void dgSrc_SelectionChanged(object sender, EventArgs e)
        {
            if (dgSrc.SelectedRows.Count == 1)
            {
                DataGridViewRow row = this.dgSrc.SelectedRows[0];
                SelectedSRC = (int)row.Cells["id"].Value;

            }
        }

        private void txtDoc_TextChanged(object sender, EventArgs e)
        {

        }

        private void txtFilter_TextChanged(object sender, EventArgs e)
        {
            if (txtFilter.Text != "")
            {
                foreach (DataGridViewRow row in dgSrc.Rows)
                {
                    if (row.Cells[0].Value.ToString().Equals(txtFilter.Text))
                    {
                        if (!dgSrc.Rows[row.Index].Visible)
                            dgSrc.FirstDisplayedScrollingRowIndex = row.Index;

                        break;
                    }
                }
            }
        }

        private void cmdFind_Click(object sender, EventArgs e)
        {
            if (txtFilter.Text != "")
            {
                foreach (DataGridViewRow row in dgSrc.Rows)
                {
                    if (row.Cells[0].Value.ToString().Equals(txtFilter.Text))
                    {
                        if (!dgSrc.Rows[row.Index].Visible)
                            dgSrc.FirstDisplayedScrollingRowIndex = row.Index;

                        break;
                    }
                }
            }
        }

        private void frmMapper_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }
    }
}
