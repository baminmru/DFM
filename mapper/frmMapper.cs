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

        public void Init(bool setupGrid = true)
        {

            DataTable dst = DS.ReadData("select table_name,field_name, comment, entity_key, entity_root from dest_data where field_name not in ('created_ts', 'created_by', 'modified_ts', 'modified_by','effective_end_date','effective_begin_date', 'is_effective', 'process_id') order by table_name, field_name");
            dgDest.DataSource = dst;
            dgDest.ClearSelection();

            DataTable src;
            if (chkUsedOnly.Checked)
            {
                src = DS.ReadData("select api, table_name,field_name,field_type, comment,entity_key, api_comment, table_comment from src_data where api in (select api from used_api)  order by api, table_name, field_name");
            }
            else
            {
                src = DS.ReadData("select api, table_name,field_name,field_type, comment,entity_key, api_comment, table_comment from src_data order by api, table_name, field_name");
            }

            dgSrc.DataSource = src;
            dgSrc.ClearSelection();


            if (setupGrid)
            {
                dgSrc.Columns["api_comment"].Visible = false;
                dgSrc.Columns["table_comment"].Visible = false;

                
                dgDest.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
                dgDest.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.DisplayedCells;
                dgDest.Columns["comment"].Width = 300;
                dgDest.Columns["comment"].DefaultCellStyle.WrapMode = DataGridViewTriState.True;
                dgDest.Columns["comment"].AutoSizeMode = DataGridViewAutoSizeColumnMode.NotSet;

                dgSrc.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
                dgSrc.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.DisplayedCells;
                dgSrc.Columns["comment"].Width = 300;
                dgSrc.Columns["comment"].DefaultCellStyle.WrapMode = DataGridViewTriState.True;
                dgSrc.Columns["comment"].AutoSizeMode = DataGridViewAutoSizeColumnMode.NotSet;
            }
            txtComment.Enabled = false;
            txtCondition.Enabled = false;
            cmdDelLink.Enabled = false;
            cmdSaveLink.Enabled = false;

            MergeGridviewCells(dgDest, new int[] { 0 });
            MergeGridviewCells(dgSrc, new int[] { 0,1 });

            ReloadMapName();

        }

        private void MergeGridviewCells(DataGridView DGV, int[] idx)
        {
            DataGridViewRow Prev = null;

            foreach (DataGridViewRow item in DGV.Rows)
            {
                if (Prev != null)
                {
                    string firstCellText = string.Empty;
                    string secondCellText = string.Empty;

                    foreach (int i in idx)
                    {
                        DataGridViewCell firstCell = Prev.Cells[i];
                        DataGridViewCell secondCell = item.Cells[i];

                        firstCellText = (firstCell != null && firstCell.Value != null ? firstCell.Value.ToString() : string.Empty);
                        secondCellText = (secondCell != null && secondCell.Value != null ? secondCell.Value.ToString() : string.Empty);

                        if (firstCellText == secondCellText)
                        {
                            secondCell.Style.ForeColor = Color.Transparent;
                        }
                        else
                        {
                            Prev = item;
                            break;
                        }
                    }
                }
                else
                {
                    Prev = item;
                }
            }
        }


        private void dgDest_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }


       
        private string DestTBL { get; set; }
        private string DestFLD { get; set; }


        private string SrcAPI { get; set; }
        private string SrcTBL { get; set; }
        private string SrcFLD { get; set; }

        private void dgDest_SelectionChanged(object sender, EventArgs e)
        {
            if (dgDest.SelectedRows.Count == 1)
            {
                DataGridViewRow row = this.dgDest.SelectedRows[0];
               DestTBL = row.Cells["table_name"].Value.ToString();
               DestFLD = row.Cells["field_name"].Value.ToString();

                



                DataTable map = DS.ReadData("select * from map_data where map_name ='" + cmbMapName.Text + "' and to_table ='" + DestTBL +"' and to_field ='" + DestFLD + "'");
                if (map.Rows.Count > 0)
                {
                    //SelectedSRC = (int)map.Rows[0]["src_id"];
                    SrcAPI = map.Rows[0]["api"].ToString();
                    SrcTBL = map.Rows[0]["table_name"].ToString();
                    SrcFLD = map.Rows[0]["field_name"].ToString();




                    dgSrc.ClearSelection();

                    foreach (DataGridViewRow srow in dgSrc.Rows)
                    {
                        try
                        {
                            if (!(srow.Cells is null) )
                            {
                                if (srow.Cells["api"].Value.ToString().Equals(SrcAPI.ToString())  &&
                                    srow.Cells["table_name"].Value.ToString().Equals(SrcTBL.ToString()) &&
                                    srow.Cells["field_name"].Value.ToString().Equals(SrcFLD.ToString()) )
                                {
                                    dgSrc.Rows[srow.Index].Selected = true;
                                    //if (!dgSrc.Rows[srow.Index].Visible)
                                    dgSrc.FirstDisplayedScrollingRowIndex = srow.Index;

                                    txtComment.Text = map.Rows[0]["comment"].ToString();
                                    txtComment.Enabled = true;
                                    
                                    txtCondition.Text = map.Rows[0]["condition"].ToString();
                                    txtCondition.Enabled = true;
                                    
                                    cmdDelLink.Enabled = true;
                                    cmdSaveLink.Enabled = true;
                                    break;
                                }
                            }
                        }
                        catch (System.Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                        }
                    }




                }
                else
                {
                    txtComment.Text = "";
                    txtComment.Enabled = false;
                    txtCondition.Text = "";
                    txtCondition.Enabled = false;

                    cmdDelLink.Enabled = false;
                    cmdSaveLink.Enabled = false;
                    dgSrc.ClearSelection();
                }


            }
        }

        private void ReloadMapName()
        {
            DataTable dt = DS.ReadData("select distinct map_name from map_data");

            string s = cmbMapName.Text;
            cmbMapName.Items.Clear();

            for(int i = 0; i < dt.Rows.Count; i++)
            {
                cmbMapName.Items.Add(dt.Rows[i]["map_name"].ToString());
                    
            }

            cmbMapName.Text = s;

        }

        private void cmdSaveLink_Click(object sender, EventArgs e)
        {
             if (dgSrc.SelectedRows.Count == 1 && dgDest.SelectedRows.Count == 1)
            {

                DS.Execute("delete from  map_data  where map_name ='" + cmbMapName.Text + "' and to_table ='" + DestTBL + "' and to_field ='" + DestFLD + "'");
                DS.Execute("insert into map_data (api,table_name,field_name, to_table, to_field,comment,condition,map_name) values('" + SrcAPI + "','" + SrcTBL + "','" + SrcFLD + "','" + DestTBL + "','" + DestFLD + "','" + txtComment.Text.Replace("'", "''")+"','" + txtCondition.Text.Replace("'", "''") + "','" + cmbMapName.Text + "')");

                cmdDelLink.Enabled = true;
                ReloadMapName();
            }
        }

        private void cmdDelLink_Click(object sender, EventArgs e)
        {
            if (dgDest.SelectedRows.Count == 1)
            {
                DS.Execute("delete from  map_data  where map_name ='" + cmbMapName.Text + "' and to_table ='" + DestTBL + "' and to_field ='" + DestFLD + "'");
                dgSrc.ClearSelection();
                txtComment.Enabled = false;
                txtCondition.Enabled = false;
                cmdDelLink.Enabled = false;
                cmdSaveLink.Enabled = false;
                ReloadMapName();
            }
        }

        private void dgSrc_SelectionChanged(object sender, EventArgs e)
        {
            if (dgSrc.SelectedRows.Count == 1)
            {
                DataGridViewRow row = this.dgSrc.SelectedRows[0];
                SrcAPI = row.Cells["api"].Value.ToString();
                SrcTBL = row.Cells["table_name"].Value.ToString();
                SrcFLD = row.Cells["field_name"].Value.ToString();

                txtComment.Enabled = true;
                txtCondition.Enabled = true;

                cmdSaveLink.Enabled = true;

            }
        }

      

        private void txtFilter_TextChanged(object sender, EventArgs e)
        {
            cmdFind_Click(sender,  e);
        }

        private void cmdFind_Click(object sender, EventArgs e)
        {
            string api = "";

            int idx = 0;


            if (cmbAPI.Text != "")
            {
                api = cmbAPI.Text;

                foreach (DataGridViewRow row in dgSrc.Rows)
                {
                    String a = row.Cells["api"].Value.ToString().ToLower();
                    if ( a.StartsWith(api.ToLower()))
                    {
                        dgSrc.FirstDisplayedScrollingRowIndex = row.Index;
                        idx = row.Index;
                        break;
                    }
                }



            }
            
            if (txtFilter.Text != "")
            {
                string[] p = txtFilter.Text.Split('.');
                for (; idx < dgSrc.Rows.Count; idx++)
                {

                    DataGridViewRow row = dgSrc.Rows[idx];
                    String v = row.Cells["table_name"].Value.ToString().ToLower();

                    if (p.Length > 1)
                    {

                        String f = row.Cells["field_name"].Value.ToString().ToLower();
                        if (v.StartsWith(p[0].ToLower()) && f.StartsWith(p[1].ToLower()))
                        {
                            dgSrc.FirstDisplayedScrollingRowIndex = row.Index;
                        }


                    }
                    else
                    {
                        if (v.StartsWith(p[0].ToLower()))
                        {
                            dgSrc.FirstDisplayedScrollingRowIndex = row.Index;
                        }
                    }

                }

            }
        }

        private void frmMapper_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void txtFindDest_TextChanged(object sender, EventArgs e)
        {
            if (txtFindDest.Text != "")
            {
                foreach (DataGridViewRow row in dgDest.Rows)
                {
                    String v = row.Cells["table_name"].Value.ToString().ToLower();
                    if (v.StartsWith(txtFindDest.Text.ToLower()))
                    {
                        //if (!dgSrc.Rows[row.Index].Visible)
                        dgDest.FirstDisplayedScrollingRowIndex = row.Index;

                        break;
                    }
                }
            }
        }

        private void cmdFindDest_Click(object sender, EventArgs e)
        {
            if (txtFindDest.Text != "")
            {
                foreach (DataGridViewRow row in dgDest.Rows)
                {
                    String v = row.Cells["table_name"].Value.ToString().ToLower();
                    if (v.StartsWith(txtFindDest.Text.ToLower()))
                    {
                        //if (!dgSrc.Rows[row.Index].Visible)
                        dgDest.FirstDisplayedScrollingRowIndex = row.Index;

                        break;
                    }
                }
            }
        }

  

        private void dgSrc_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            String v;
            if ((e.ColumnIndex == this.dgSrc.Columns["table_name"].Index))
            {
                DataGridViewCell cell = this.dgSrc.Rows[e.RowIndex].Cells[e.ColumnIndex];
                v = this.dgSrc.Rows[e.RowIndex].Cells["table_comment"].Value.ToString();
                if (v != "")
                {
                    cell.ToolTipText = v;
                }
            }

            if ((e.ColumnIndex == this.dgSrc.Columns["api"].Index))
            {
                DataGridViewCell cell = this.dgSrc.Rows[e.RowIndex].Cells[e.ColumnIndex];
                v = this.dgSrc.Rows[e.RowIndex].Cells["api_comment"].Value.ToString();
                if (v != "")
                {
                    cell.ToolTipText = v;
                }
            }
        }

        private void cmdRefresh_Click(object sender, EventArgs e)
        {
            Init();
        }

        private void generatePtablesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            dlgSave.Filter = "SQL files|*.sql";
            if(dlgSave.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string fn = dlgSave.FileName;
                    PGGen g = new PGGen();
                    g.ds = DS;
                    string sql = g.GenerateAll();
                    File.WriteAllText(fn, sql);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error generating tables: " + ex.Message);
                }
            }
        }

        private void mnuGenViews_Click(object sender, EventArgs e)
        {
            dlgSave.Filter = "SQL files|*.sql";
            if (dlgSave.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string fn = dlgSave.FileName;
                    viewGen g = new viewGen();
                    g.ds = DS;
                    string sql = g.GenerateAll();
                    File.WriteAllText(fn, sql);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error generating views: " + ex.Message);
                }
            }
        }

        private void cmbMapName_TextChanged(object sender, EventArgs e)
        {
            if (cmbMapName.Text != "")
                dgDest_SelectionChanged(sender, e);
        }

        private void mnuRefresh_Click(object sender, EventArgs e)
        {
            Init();
        }

        private void textAPIMAsk_TextChanged(object sender, EventArgs e)
        {
            DataTable api;
            if (chkUsedOnly.Checked)
            {
                if (txtAPIMask.Text != "")
                    api = DS.ReadData("select distinct api from used_api where api like '" + txtAPIMask.Text + "' order by api");
                else
                    api = DS.ReadData("select distinct api from used_api  order by api");
            }
            else
            {

                if (txtAPIMask.Text != "")
                    api = DS.ReadData("select distinct api from src_data where api like '" + txtAPIMask.Text + "' order by api");
                else
                    api = DS.ReadData("select distinct api from src_data  order by api");
            }

            cmbAPI.ValueMember = "api";
            cmbAPI.DisplayMember = "api";
            cmbAPI.DataSource = api;
            

        }

        private void cmbAPI_SelectedIndexChanged(object sender, EventArgs e)
        {
            cmdFind_Click(sender, e);
        }

        private void chkUsedOnly_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void cmdToUsed_Click(object sender, EventArgs e)
        {
            if( cmbAPI.Text != "")
            {
                try
                {
                    DS.Execute("insert into used_api (api) values ('" + cmbAPI.Text + "')");
                    Init();
                }
                catch (System.Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
                
            }
        }

        private void mnuBuildMap_Click(object sender, EventArgs e)
        {
            dlgSave.Filter = "SQL files|*.sql";
            if (dlgSave.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string fn = dlgSave.FileName;
                    PGGen g = new PGGen();
                    g.ds = DS;
                    string sql = g.BuildMap();
                    File.WriteAllText(fn, sql);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error building map: " + ex.Message);
                }
            }
        }

        private void dgSrc_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
                
            DataGridViewRow row = this.dgSrc.Rows[e.RowIndex];
            DataGridViewColumn c = this.dgSrc.Columns[e.ColumnIndex];
            if (c.Name == "entity_key")
            {

                SrcAPI = row.Cells["api"].Value.ToString();
                SrcTBL = row.Cells["table_name"].Value.ToString();
                SrcFLD = row.Cells["field_name"].Value.ToString();

                //if (MessageBox.Show("Изменить признак ключа для поля " + SrcFLD + " таблицы " + SrcFLD +"?","Подтвердите",MessageBoxButtons.YesNoCancel) == DialogResult.Yes)
                {

                    if ((bool)row.Cells["entity_key"].Value)
                    {
                        DS.Execute("update src_data set entity_key = false where api='" + SrcAPI + "' and table_name = '" + SrcTBL + "' and field_name ='" + SrcFLD + "'");
                        row.Cells["entity_key"].Value = false;
                    }
                    else
                    {
                        DS.Execute("update src_data set entity_key = true where api='" + SrcAPI + "' and table_name = '" + SrcTBL + "' and field_name ='" + SrcFLD + "'");
                        row.Cells["entity_key"].Value = true;
                    }
                }
            }
        }

        private void dgDest_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            DataGridViewRow row = this.dgDest.Rows[e.RowIndex];
            DataGridViewColumn c = this.dgDest.Columns[e.ColumnIndex];
            DestTBL = row.Cells["table_name"].Value.ToString();
            DestFLD = row.Cells["field_name"].Value.ToString();

            if (c.Name == "entity_key")
            {

                if ((bool)row.Cells["entity_key"].Value)
                {
                    DS.Execute("update dest_data set entity_key = false where  table_name = '" + DestTBL + "' and field_name ='" + DestFLD + "'");
                    row.Cells["entity_key"].Value = false;
                }
                else
                {
                    DS.Execute("update dest_data set entity_key = true where  table_name = '" + DestTBL + "' and field_name ='" + DestFLD + "'");
                    row.Cells["entity_key"].Value = true;
                }
            }


            if (c.Name == "entity_root")
            {

                if ((bool)row.Cells["entity_root"].Value)
                {
                    DS.Execute("update dest_data set entity_root = false where  table_name = '" + DestTBL + "' and field_name ='" + DestFLD + "'");
                    row.Cells["entity_root"].Value = false;
                }
                else
                {
                    DS.Execute("update dest_data set entity_root = true where  table_name = '" + DestTBL + "' and field_name ='" + DestFLD + "'");
                    row.Cells["entity_root"].Value = true;
                }
            }

        }
    }
}
