package org.isf.reductionplan.gui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.isf.generaldata.MessageBundle;
import org.isf.menu.manager.Context;
import org.isf.reductionplan.manager.ReductionplanBrowserManager;
import org.isf.reductionplan.model.ReductionPlan;
import org.isf.utils.exception.OHServiceException;
import org.isf.utils.exception.gui.OHServiceExceptionUtil;
import org.isf.utils.jobjects.ModalJFrame;

public class ReductionPlanBrowser extends ModalJFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton jNewButton;
	private JButton jEditButton;
	private JButton jDeteleButton;
	private JButton jCloseButton;
	List<ReductionPlan> reductionPlanList;

	private final String[] pbiColumn = new String[] {
					MessageBundle.getMessage("angal.common.code.txt"),
					MessageBundle.getMessage("angal.common.description.txt"),
					MessageBundle.getMessage("angal.reduction.medicalrate"),
					MessageBundle.getMessage("angal.reduction.examrate"),
					MessageBundle.getMessage("angal.reduction.operate"),
					MessageBundle.getMessage("angal.reduction.otherrate")
	};

	private final int[] pColumwidth = { 80, 200, 80, 80, 80, 80 };
	private final ReductionplanBrowserManager manager = Context.getApplicationContext().getBean(ReductionplanBrowserManager.class);
	public ReductionPlanBrowser() {
		initialize();
	}

	private void initialize() {
		setTitle (MessageBundle.getMessage("angal.reductionplan.reductionplanbrowser.title"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 620, 300);
		setContentPane(getMainContentPane());
		setLocationRelativeTo(null);
	}

	private JPanel getMainContentPane() {
		try {
			if (contentPane == null) {
				contentPane = new JPanel();
				// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				contentPane.setLayout(new BorderLayout());

				scrollPane = new JScrollPane();
				contentPane.add(scrollPane, BorderLayout.CENTER);

				table = new JTable();
				table.setModel(new ReductionPlanModel());

				for (int i = 0; i < pbiColumn.length; i++) {
					table.getColumnModel().getColumn(i).setMinWidth(pColumwidth[i]);
					table.getColumnModel().getColumn(i).setMaxWidth(pColumwidth[i]);
				}

				scrollPane.setViewportView(table);
				contentPane.add(getButtonPane(), BorderLayout.SOUTH);
			}
		} catch (OHServiceException e) {
			OHServiceExceptionUtil.showMessages(e);
		}
		return contentPane;
	}
	private JPanel getButtonPane() {
		JPanel panel = new JPanel();
		panel.add(getJNewButton());
		panel.add(getJEditButton());
		panel.add(getJDeteleButton());
		panel.add(getJCloseButton());
		return panel;
	}

	private JButton getJNewButton() {
		if (jNewButton == null) {
			jNewButton  = new JButton(MessageBundle.getMessage("angal.common.new.btn"));
			jNewButton .setMnemonic(MessageBundle.getMnemonic("angal.common.new.btn.key"));
			jNewButton.setEnabled(false);
		}
		return jNewButton;
	}

	private JButton getJEditButton() {
		if (jEditButton == null) {
			jEditButton = new JButton(MessageBundle.getMessage("angal.common.edit.btn"));
			jEditButton.setMnemonic(MessageBundle.getMnemonic("angal.common.edit.btn.key"));
			jEditButton.setEnabled(false);
		}
		return jEditButton;
	}

	private JButton getJDeteleButton() {
		if (jDeteleButton == null) {
			jDeteleButton = new JButton(MessageBundle.getMessage("angal.common.delete.btn"));
			jDeteleButton.setMnemonic(MessageBundle.getMnemonic("angal.common.delete.btn.key"));
			jDeteleButton.setEnabled(false);
		}
		return jDeteleButton;
	}

	private JButton getJCloseButton() {
		if (jCloseButton == null) {
			jCloseButton = new JButton();
			jCloseButton = new JButton(MessageBundle.getMessage("angal.common.close.btn"));
			jCloseButton.setMnemonic(MessageBundle.getMnemonic("angal.common.close.btn.key"));
			jCloseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		return jCloseButton;
	}

	private class ReductionPlanModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		public ReductionPlanModel() throws OHServiceException {
			reductionPlanList = manager.getReductionplan();
		}

		public int getRowCount() {
			if (reductionPlanList == null)
				return 0;
			return reductionPlanList.size();
		}

		public String getColumnName(int c) {
			return pbiColumn[c];
		}

		public int getColumnCount() {
			return pbiColumn.length;
		}


		public Object getValueAt(int r, int c) {
			if (c == 0) {
				return reductionPlanList.get(r).getId();
			} else if (c == -1) {
				return reductionPlanList.get(r);
			} else if (c == 1) {
				return reductionPlanList.get(r).getDescription();
			} else if (c == 2) {
				return reductionPlanList.get(r).getMedicalRate();
			} else if (c == 3) {
				return reductionPlanList.get(r).getExamRate();
			} else if (c == 4) {
				return reductionPlanList.get(r).getOperationRate();
			} else if (c == 5) {
				return reductionPlanList.get(r).getOtherRate();
			}
			return null;
		}

		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}
	}


}




