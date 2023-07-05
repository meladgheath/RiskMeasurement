package org.softwareengine.utils.service;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.modules.warehouse.model.Stock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class reportService {

    public  JasperPrint getDistrubumentReport (List<Transaction> lists,String line) throws JRException {
        JasperReport reports = null ;
        JasperPrint jp = null ;
        reports = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/transactions.jrxml")
        );
        jp = JasperFillManager.fillReport(reports,null, DatabaseService.connection);


   /*     List<TransactionTemplate> ls = new ArrayList<>();
        int i = 0 ;
        while (i < lists.size()) {
            ls.add(new TransactionTemplate(
                    i+1, // seqence number
                    lists.get(i).product().name(),
                    lists.get(i).debit(),
                    lists.get(i).credit(),
                    lists.get(i).quantity(),
                    lists.get(i).store().name(),
                    lists.get(i).treasury().name(),
                    lists.get(i).type().name(),
                    lists.get(i).billNum(),
                    lists.get(i).customer().name(),
                    lists.get(i).supplier().name(),
                    lists.get(i).paymentMethod().name()
            ));
            i++ ;
        }*/

        HashMap params = new HashMap();
//        params.put("REPORT_RESOURCE_BUNDLE", lang.get());
          params.put("line",line);


        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lists);
        JasperPrint print = JasperFillManager.fillReport(reports,params,dataSource);
        return print ;
    }

    public JasperPrint getReport (String name , double money,String title) throws JRException, IOException, SQLException {


        JasperReport reports = null ;
        JasperPrint jp = null ;

        reports = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/Money.jrxml")
        );

        jp = JasperFillManager.fillReport(reports,null, DatabaseService.connection);


        reports.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        Map<String,Object> para = new HashMap<>();

        System.out.println("the money is : $$"+money);
        para.put("name",name);
        para.put("money",money) ;
        para.put("title",title);

        JasperPrint print = JasperFillManager.fillReport(reports,para);
        return print ;
    }
    public JasperPrint getStoreReport(List<Stock> ls) throws JRException {
        JasperReport report = null ;
        JasperPrint jp = null ;
        report = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/store.jrxml")
        );
        jp = JasperFillManager.fillReport(report,null);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ls);
        JasperPrint print = JasperFillManager.fillReport(report,null,dataSource);

        return print ;
    }

}
