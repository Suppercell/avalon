package com.avalon.common.generator;

import java.util.List;

import cn.org.rapid_framework.generator.Generator;
import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;

import com.avalon.common.generator.constants.PropertyConstant;
import com.avalon.common.generator.util.GeneratorUtil;

/**
 * Created by admin on 2016/3/7.
 *
 */
public class GeneratorFactory {

    private static GeneratorFactory factory = null;

    private GeneratorFacade         facade  = null;

    public GeneratorFactory() {
        facade = new GeneratorFacade();
    }

    public static GeneratorFactory getInstance() {
        if (factory == null) {
            factory = new GeneratorFactory();
        }
        return factory;
    }

    public void create() throws Exception {
        cn.org.rapid_framework.generator.Generator generator = new Generator();
        generator.setOutRootDir(GeneratorProperties.getProperty(PropertyConstant.OUTPUT));
        generator.setTemplateRootDir(GeneratorProperties.getProperty(PropertyConstant.TEMPLATE));
        facade.setGenerator(generator);

        //刪除输出目录
        facade.deleteOutRootDir();

        //根据表格生成代码
        String allSwitch = GeneratorProperties.getProperty(PropertyConstant.ALL_SWITCH);
        if ("true".equals(allSwitch)) {
            List<Table> tables = TableFactory.getInstance().getAllTables();
            for (Table table : tables) {
                GeneratorUtil.removeTablePrefix(table,
                    GeneratorProperties.getProperty(PropertyConstant.PREFIX).split(","));
                Generator.GeneratorModel m = GeneratorFacade.GeneratorModelUtils.newGeneratorModel(
                    "table", table);
                facade.generateBy(m);
            }

        }
        if ("false".equals(allSwitch)) {
            if (!"".equals(GeneratorProperties.getProperty(PropertyConstant.TABLES))
                || null != GeneratorProperties.getProperty(PropertyConstant.TABLES)) {
                String[] tableNames = GeneratorProperties.getProperty(PropertyConstant.TABLES)
                    .split(",");
                Table table = null;

                for (String tableName : tableNames) {
                    table = TableFactory.getInstance().getTable(tableName);
                    GeneratorUtil.removeTablePrefix(table,
                        GeneratorProperties.getProperty(PropertyConstant.PREFIX).split(","));
                    Generator.GeneratorModel m = GeneratorFacade.GeneratorModelUtils
                        .newGeneratorModel("table", table);
                    facade.generateBy(m);
                }
            } else {
                throw new RuntimeException(PropertyConstant.TABLES + "值不存在");
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            GeneratorFactory.getInstance().create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
