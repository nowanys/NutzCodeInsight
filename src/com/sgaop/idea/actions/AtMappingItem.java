package com.sgaop.idea.actions;

import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.PsiElementNavigationItem;
import com.intellij.pom.Navigatable;
import com.intellij.pom.PomTargetPsiElement;
import com.intellij.psi.PsiElement;
import com.sgaop.util.IconsUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:53
 */
public class AtMappingItem implements PsiElementNavigationItem, ItemPresentation {

    private PsiElement targetElement;
    private PsiElement containingFilePsiElement;
    private Navigatable navigatable;
    private String urlPath;
    private String requestMethod;

    public AtMappingItem(PsiElement targetElement, String urlPath, String requestMethod) {
        this.targetElement = targetElement;
        this.navigatable = (Navigatable) targetElement;
        this.containingFilePsiElement = targetElement.getContainingFile().getOriginalElement();
        this.requestMethod = requestMethod.trim();
        this.urlPath = urlPath.replaceAll("//", "/");
    }


    @Override
    public PsiElement getTargetElement() {
        return targetElement;
    }

    @Nullable
    @Override
    public String getName() {
        return this.urlPath + " " + this.requestMethod;
    }

    @Override
    public void navigate(boolean b) {
        navigatable.navigate(b);
    }

    @Override
    public boolean canNavigate() {
        return navigatable.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return this;
    }

    @Nullable
    @Override
    public String getPresentableText() {
        return urlPath + " (" + requestMethod + ")";
    }

    @Nullable
    @Override
    public String getLocationString() {
        return "(in " + targetElement.getContainingFile().getName() + ")";
    }

    @Nullable
    @Override
    public Icon getIcon(boolean b) {
        return IconsUtil.NUTZ;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
