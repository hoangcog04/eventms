package com.example.eventms.mbp.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.annotations.Mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

import static com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel;

/**
 * MyBatis Plus generator
 * Created by vicendy04 on 2025-03.
 */
public class MyBatisPlusGenerator {
    public static void main(String[] args) throws IOException {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "generator.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));
        String url = properties.getProperty("jdbc.connectionURL");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        String projectPath = System.getProperty("user.dir");
        String modulePath = projectPath + "/eventms-mbp";
        String author = "vicendy04";
        String parent = properties.getProperty("package.base");

        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(url, username, password);
        fastAutoGenerator.dataSourceConfig(getDataSourceConfig());
        fastAutoGenerator.globalConfig(getGlobalConfig(modulePath, author));
        fastAutoGenerator.packageConfig(getPackageConfig(parent, modulePath));
        fastAutoGenerator.strategyConfig(getStrategyConfig());
        fastAutoGenerator.templateEngine(new FreemarkerTemplateEngine());
        fastAutoGenerator.execute();
    }

    public static Consumer<DataSourceConfig.Builder> getDataSourceConfig() {
        return builder -> {
            builder.dbQuery(new MySqlQuery());
            builder.typeConvert(new MySqlTypeConvert());
        };
    }

    public static Consumer<GlobalConfig.Builder> getGlobalConfig(String modulePath, String author) {
        return builder -> {
            builder.disableOpenDir();
            //builder.enableSwagger();
            builder.enableSpringdoc();
            builder.outputDir(modulePath + "/src/main/java");
            builder.author(author);
            builder.commentDate("yyyy-MM");
        };
    }

    public static Consumer<PackageConfig.Builder> getPackageConfig(String parent, String modulePath) {
        return builder -> {
            builder.parent(parent);
            builder.pathInfo(Collections.singletonMap(OutputFile.xml, modulePath + "/src/main/resources/mapper/"));
        };
    }

    public static Consumer<StrategyConfig.Builder> getStrategyConfig() {
        return builder -> {
            builder.entityBuilder()
                    .enableFileOverride()
                    .enableLombok()
                    .naming(underline_to_camel)
                    .columnNaming(underline_to_camel)
                    .fieldUseJavaDoc(false)
                    .idType(IdType.AUTO)
                    .logicDeleteColumnName("deleted")
                    .addTableFills(new Column("created", FieldFill.INSERT))
                    .addTableFills(new Column("changed", FieldFill.INSERT_UPDATE));
            builder.mapperBuilder()
                    .enableFileOverride()
                    .mapperAnnotation(Mapper.class)
                    .enableBaseResultMap();
            builder.serviceBuilder()
                    .disable();
            builder.controllerBuilder()
                    .disable();
        };
    }
}
