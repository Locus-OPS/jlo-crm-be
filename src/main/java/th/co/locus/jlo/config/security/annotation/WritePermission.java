package th.co.locus.jlo.config.security.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

import th.co.locus.jlo.config.security.JLOPermissionEvaluator;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasPermission(null, '" + JLOPermissionEvaluator.WRITE + "')")
public @interface WritePermission {

}