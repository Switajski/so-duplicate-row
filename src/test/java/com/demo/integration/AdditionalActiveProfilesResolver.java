package com.demo.integration;

import static org.springframework.test.context.TestContextAnnotationUtils.findAnnotationDescriptor;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.test.context.TestContextAnnotationUtils.AnnotationDescriptor;
import org.springframework.util.Assert;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * DefaultActiveProfilesResolver overrides the active profiles, while this class enables adding to the active profiles
 * as specified in the environment.<br/> <br/> E.g. {@code @ActiveProfile("a,b")} run with SPRING_PROFILES_ACTIVE=c,d
 * will activate a,b,c,d (note that the first named profile precedes).
 *
 * @author Robert Heim
 */
@Slf4j
public class AdditionalActiveProfilesResolver implements ActiveProfilesResolver {

    /**
     * Comma-separated string splitter
     **/
    private static final Splitter COMMA_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

    @Override
    public String[] resolve(final Class<?> testClass) {
        Assert.notNull(testClass, "Class must not be null");

        final Set<String> activeProfiles = Sets.newLinkedHashSet();

        // load environment variable profile
        final String envVarProfiles = System.getenv("SPRING_PROFILES_ACTIVE");
        if (!StringUtils.isEmpty(envVarProfiles)) {
            log.trace("Active profiles in environment variable: {}", envVarProfiles);
            activeProfiles.addAll(COMMA_SPLITTER.splitToList(envVarProfiles));
        }

        // load environment profile
        final String envProfiles = System.getenv("spring.profiles.active");
        if (!StringUtils.isEmpty(envProfiles)) {
            log.trace("Active profiles in environment2 properties: {}", envProfiles);
            activeProfiles.addAll(COMMA_SPLITTER.splitToList(envProfiles));
        }

        // load system profiles
        final String sysProfiles = System.getProperty("spring.profiles.active");
        if (!StringUtils.isEmpty(sysProfiles)) {
            log.trace("Active profiles in system properties: {}", sysProfiles);
            activeProfiles.addAll(COMMA_SPLITTER.splitToList(sysProfiles));
        }

        // append @ActiveProfiles annotations
        final AnnotationDescriptor<ActiveProfiles> descriptor = findAnnotationDescriptor(
                testClass,
                ActiveProfiles.class);
        if (descriptor != null) {
            final ActiveProfiles annotation = descriptor.getAnnotation();
            final String[] profiles = annotation.profiles();
            log.trace(
                    "Active profiles in {} (via @ActiveProfiles): {}",
                    testClass.getSimpleName(),
                    Joiner.on(", ").join(profiles));
            for (final String profile : profiles) {
                if (!StringUtils.isEmpty(profile)) {
                    activeProfiles.add(profile.trim());
                }
            }
        }

        log.trace("Merged active profiles: {}", activeProfiles);
        return activeProfiles.toArray(new String[activeProfiles.size()]);
    }
}
