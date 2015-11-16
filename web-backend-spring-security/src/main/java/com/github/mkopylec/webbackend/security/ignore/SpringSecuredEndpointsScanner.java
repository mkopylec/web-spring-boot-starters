package com.github.mkopylec.webbackend.security.ignore;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;

public class SpringSecuredEndpointsScanner implements SecuredEndpointsScanner {

    private final ApplicationContext context;

    public SpringSecuredEndpointsScanner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public List<String> scanForSecuredEndpoints() {
        List<String> securedEndpoints = new ArrayList<>();
        for (String packageName : findComponentScanPackages()) {
            securedEndpoints.addAll(findSecuredEndpoints(packageName));
        }
        return securedEndpoints;
    }

    private Set<String> findSecuredEndpoints(String packageName) {
        Set<String> securedEndpoints = new HashSet<>();

        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner(), new SubTypesScanner(), new MethodAnnotationsScanner());

        Map<Class, String> typeRequestPaths = new HashMap<>();
        Map<Class, String> methodRequestPaths = new HashMap<>();

        Set<Class<?>> authorizedClasses = reflections.getTypesAnnotatedWith(PreAuthorize.class);
        for (Class<?> clazz : authorizedClasses) {
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                for (String path : requestMapping.value()) {
                    typeRequestPaths.put(clazz, path);
                }
            }
        }

        Set<Method> authorizedMethods = reflections.getMethodsAnnotatedWith(PreAuthorize.class);
        for (Method method : authorizedMethods) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                for (String path : requestMapping.value()) {
                    methodRequestPaths.put(method.getDeclaringClass(), path);
                }
            }
        }

        for (Entry<Class, String> typeRequestPath : typeRequestPaths.entrySet()) {
            String securedPath = typeRequestPath.getValue();
            if (securedPath != null && methodRequestPaths.containsKey(typeRequestPath.getKey())) {
                securedPath += methodRequestPaths.get(typeRequestPath.getKey());
            }
            securedEndpoints.add(securedPath);
        }

        return securedEndpoints;
    }

    private List<String> findComponentScanPackages() {
        Map<String, Object> componentScanBeans = context.getBeansWithAnnotation(ComponentScan.class);
        if (isNotEmpty(componentScanBeans)) {
            List<String> packages = new ArrayList<>(componentScanBeans.size());
            for (Entry<String, Object> bean : componentScanBeans.entrySet()) {
                String packageName = bean.getValue().getClass().getPackage().getName();
                packages.add(packageName);
            }
            return packages;
        }
        return emptyList();
    }
}
