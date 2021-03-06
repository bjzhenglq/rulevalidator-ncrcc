package ncmdp.views.busiactivity;

import ncmdp.editor.NCMDPEditor;
import ncmdp.model.activity.RefBusiOperation;
import ncmdp.tool.basic.StringUtil;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

public class BusiActTableModifier implements ICellModifier {
	TableViewer tableViewer = null;

	public static String[] columnProiprtties = new String[] { "name", "displayName", "opInterface",
			"description", "authorization" };

	public BusiActTableModifier(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	@Override
	public boolean canModify(Object element, String property) {
		if (columnProiprtties[4].equalsIgnoreCase(property)) { return true; }
		return false;
	}

	@Override
	public Object getValue(Object element, String property) {
		RefBusiOperation refOp = (RefBusiOperation) element;
		if (property.equals(columnProiprtties[0])) {
			return refOp.getName();
		} else if (property.equals(columnProiprtties[1])) {
			return refOp.getDisplayName();
		} else if (property.equals(columnProiprtties[2])) {
			return refOp.getName();
		} else if (property.equals(columnProiprtties[3])) {
			return refOp.getRealBusiOpid();
		} else if (property.equals(columnProiprtties[4])) { return refOp.isAuthorization(); }
		return null;
	}

	@Override
	public void modify(Object element, String property, Object value) {
		if (value == null || StringUtil.isEmptyWithTrim(value.toString())) { return; }
		TableItem item = (TableItem) element;
		RefBusiOperation refOp = (RefBusiOperation) item.getData();
		if (columnProiprtties[4].equals(property)) {
			boolean isChange = refOp.isAuthorization() | (Boolean) value;
			if (isChange) {
				refOp.setAuthorization((Boolean) value);
				NCMDPEditor.getActiveMDPEditor().setDirty(true);
				tableViewer.update(refOp, null);
			}
		}
	}

}
