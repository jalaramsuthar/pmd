/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.metrics.impl;

import java.util.List;

import net.sourceforge.pmd.lang.java.ast.ASTAnyTypeDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodOrConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.JavaQualifiedName;
import net.sourceforge.pmd.lang.java.metrics.signature.JavaOperationSigMask;
import net.sourceforge.pmd.lang.java.metrics.signature.JavaOperationSignature.Role;
import net.sourceforge.pmd.lang.java.metrics.signature.JavaSignature.Visibility;
import net.sourceforge.pmd.lang.metrics.MetricVersion;

/**
 * Access to Foreign Data. Quantifies the number of foreign fields accessed directly or via accessors.
 *
 * @author Clément Fournier
 */
public final class AtfdMetric {


    public static final class AtfdOperationMetric extends AbstractJavaOperationMetric {

        @Override // TODO:cf
        public double computeFor(ASTMethodOrConstructorDeclaration node, MetricVersion version) {

            JavaOperationSigMask targetOps = new JavaOperationSigMask();
            targetOps.restrictVisibilitiesTo(Visibility.PUBLIC);
            targetOps.restrictRolesTo(Role.GETTER_OR_SETTER);

            List<JavaQualifiedName> callQNames = findAllCalls(node);
            int foreignCalls = 0;
            for (JavaQualifiedName name : callQNames) {
                if (getSignatureMatcher().hasMatchingSig(name, targetOps)) {
                    foreignCalls++;
                }
            }

            return foreignCalls / callQNames.size();
        }
    }

    public static final class AtfdClassMetric extends AbstractJavaClassMetric {

        @Override
        public double computeFor(ASTAnyTypeDeclaration node, MetricVersion version) {
            // TODO:cf
            return 0;
        }


    }


}