using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace dv21_load
{
    public partial class frmSortList : Form
    {
        public frmSortList()
        {
            InitializeComponent();
        }

        private void frmSortList_Load(object sender, EventArgs e)
        {

        }

        private void btnMoveUp_Click(object sender, EventArgs e)
        {
            int selectedIndex = listBox1.SelectedIndex;
            if (selectedIndex > 0)
            {
                object selectedItem = listBox1.SelectedItem;
                listBox1.Items.RemoveAt(selectedIndex);
                listBox1.Items.Insert(selectedIndex - 1, selectedItem);
                listBox1.SelectedIndex = selectedIndex - 1;
                listBox1.Focus(); // Ensure ListBox keeps focus for visual feedback
            }
        }

        private void btnMoveDown_Click(object sender, EventArgs e)
        {
            int selectedIndex = listBox1.SelectedIndex;
            if (selectedIndex < listBox1.Items.Count - 1 && selectedIndex != -1)
            {
                object selectedItem = listBox1.SelectedItem;
                listBox1.Items.RemoveAt(selectedIndex);
                listBox1.Items.Insert(selectedIndex + 1, selectedItem);
                listBox1.SelectedIndex = selectedIndex + 1;
                listBox1.Focus(); // Ensure ListBox keeps focus for visual feedback
            }
        }
    }
}
