package com.sgaop.idea.codeinsight.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.ex.FileChooserDialogImpl;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import com.sgaop.idea.codeinsight.util.NutzLineUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2017/12/29  11:54
 */
public class NutzGutterIconNavigationHandler implements GutterIconNavigationHandler {

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        if (NutzLineUtil.isAtOk(psiElement)) {
            final Project project = psiElement.getProject();
            final List<VirtualFile> fileList = NutzLineUtil.findTemplteFileList(psiElement);
            if (fileList.size() == 1) {
                FileEditorManager.getInstance(project).openFile(fileList.get(0), true);
            } else if (fileList.size() > 1) {
//                FileChooserDescriptor descriptor=new FileChooserDescriptor();
//                FileChooserDialog fileChooserDialog = new FileChooserDialogImpl();
                final List<VirtualFile> infos = new ArrayList<>(fileList);
                final JBList list = new JBList(infos);
                list.setFixedCellHeight(UIUtil.LIST_FIXED_CELL_HEIGHT);
                PopupChooserBuilder builder = JBPopupFactory.getInstance().createListPopupBuilder(list);
                list.installCellRenderer(vfile -> {
                    if (vfile instanceof VirtualFile) {
                        VirtualFile tempVfile = (VirtualFile) vfile;
                        Icon icon = NutzLineUtil.getTemplateIcon(tempVfile.getExtension());
                        final String name = tempVfile.getName();
                        final String path = tempVfile.getCanonicalPath()
                                .replace(project.getBasePath(), "")
                                .replace("/src/main/webapp/", "    ")
                                .replace("/src/main/resources/", "    ")
                                .replace("WEB-INF/", "    ");
                        final JBLabel nameLable = new JBLabel(path, icon, SwingConstants.LEFT);
                        nameLable.setComponentStyle(UIUtil.ComponentStyle.REGULAR);
                        nameLable.setBorder(JBUI.Borders.empty(2));
                        return nameLable;
                    }
                    return new JPanel();
                });
                builder.setItemChoosenCallback(() -> {
                    final VirtualFile value = (VirtualFile) list.getSelectedValue();
                    FileEditorManager.getInstance(project).openFile(value, true);
                }).createPopup().show(new RelativePoint(mouseEvent));
            } else {
                if (fileList == null || fileList.size() <= 0) {
                    JOptionPane.showMessageDialog(null, "没有找到这个模版文件，请检查！", "错误提示", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        }
    }
}
