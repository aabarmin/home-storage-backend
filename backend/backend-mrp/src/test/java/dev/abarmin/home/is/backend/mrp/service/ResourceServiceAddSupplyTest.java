package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ResourceServiceImpl.class,
    MethodValidationPostProcessor.class,
    LeftoverFactory.class,
    ConsignmentUpdater.class
})
@EnableConfigurationProperties(MrpLeftoverCreationProperties.class)
@TestPropertySource(properties = {
    "mrp.leftover.planning-horizon=10"
})
class ResourceServiceAddSupplyTest {

}