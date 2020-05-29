package com.sgaop.idea.restful.window.tool;

import com.sgaop.idea.gotosymbol.AtMappingNavigationItem;
import com.sgaop.idea.restful.tree.ApiMutableTreeNode;
import com.sgaop.idea.restful.tree.TreeObjectType;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/22
 */
public class ApiTreeMouseAdapter extends MouseAdapter {

    private final JTree apiTree;

    public ApiTreeMouseAdapter(JTree apiTree) {
        this.apiTree = apiTree;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 如果在这棵树上点击了2次,即双击
        if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == apiTree && e.getClickCount() == 2) {
            // 按照鼠标点击的坐标点获取路径
            TreePath selPath = apiTree.getPathForLocation(e.getX(), e.getY());
            // 谨防空指针异常!双击空白处是会这样
            if (Objects.nonNull(selPath)) {
                // 获取这个路径上的最后一个组件,也就是双击的地方
                ApiMutableTreeNode node = (ApiMutableTreeNode) selPath.getLastPathComponent();
                if (Objects.nonNull(node)) {
                    TreeObjectType treeObjectType = node.getTreeObjectType();
                    if (!(treeObjectType == TreeObjectType.MODULE || treeObjectType == TreeObjectType.ROOT)) {
                        AtMappingNavigationItem el = node.getAtMappingNavigationItem();
                        if (Objects.nonNull(node) && el.canNavigate()) {
                            el.navigate(true);
                        }
                    }
                }
            }
        }
    }
}
