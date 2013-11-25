/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.dal.dao;

import it.openprj.jTicketing.blogic.exceptions.DAException;
import it.openprj.jTicketing.blogic.model.dal.connectionfactory.ConnectionManager;
import it.openprj.jTicketing.blogic.model.entity.ReportisticaLuoghiInteresse;
import it.openprj.jTicketing.blogic.model.entity.ReportisticaTickets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReportisticaDAO {

	Logger log = Logger.getLogger(this.getClass());
	Locale country = Locale.getDefault();
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"resources\\locale.xml"});
	
	public List<ReportisticaLuoghiInteresse> selectReportisticaLuoghiInteresse(long uid) throws DAException , SQLException {
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		ReportisticaLuoghiInteresse luogoInteresseRetrieved = null;
		List<ReportisticaLuoghiInteresse> listaReportisticaLuoghiInteresse = new ArrayList<ReportisticaLuoghiInteresse>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionManager.getPooledConnection();
			sql.append(" SELECT DISTINCT LI.UID_LUOGHI_INTERESSE , TITOLO , DESCRIZIONE ");  
			sql.append(" FROM TA_LUOGHI_OPERATORI LO , LUOGHI_INTERESSE LI ");
			sql.append(" WHERE LO.UID_LUOGHI_INTERESSE = LI.UID_LUOGHI_INTERESSE AND LO.UID_OPERATORE = ? ");
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1 , uid);
			rs = ps.executeQuery();
			while (rs.next()) {
				luogoInteresseRetrieved = new ReportisticaLuoghiInteresse();
				luogoInteresseRetrieved.setUid(rs.getString("UID_LUOGHI_INTERESSE"));
				luogoInteresseRetrieved.setTitolo(rs.getString("TITOLO"));
				luogoInteresseRetrieved.setDescrizione(rs.getString("DESCRIZIONE"));
				listaReportisticaLuoghiInteresse.add(luogoInteresseRetrieved);
			}
		} catch (SQLException sqlE) {
			//log.error(sqlE);
			log.error(context.getMessage("error.priceIsRequired", null, country)+ " : " + sqlE);
			throw new DAException(sqlE.getMessage());
		} finally {
			ps.close();
			rs.close();
			con.close();
		}
		return listaReportisticaLuoghiInteresse;
	}
	
	public List<ReportisticaLuoghiInteresse> selectReportisticaLuoghiInteresseAdmin() throws DAException , SQLException {
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		ReportisticaLuoghiInteresse luogoInteresseRetrieved = null;
		List<ReportisticaLuoghiInteresse> listaReportisticaLuoghiInteresse = new ArrayList<ReportisticaLuoghiInteresse>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionManager.getPooledConnection();
			sql.append(" SELECT UID_LUOGHI_INTERESSE , TITOLO , DESCRIZIONE ");  
			sql.append(" FROM LUOGHI_INTERESSE LI ");
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				luogoInteresseRetrieved = new ReportisticaLuoghiInteresse();
				luogoInteresseRetrieved.setUid(rs.getString("UID_LUOGHI_INTERESSE"));
				luogoInteresseRetrieved.setTitolo(rs.getString("TITOLO"));
				luogoInteresseRetrieved.setDescrizione(rs.getString("DESCRIZIONE"));
				listaReportisticaLuoghiInteresse.add(luogoInteresseRetrieved);
			}
		} catch (SQLException sqlE) {
			//log.error(sqlE);
			log.error(context.getMessage("error.priceIsRequired", null, country)+ " : " + sqlE);
			throw new DAException(sqlE.getMessage());
		} finally {
			ps.close();
			rs.close();
			con.close();
		}
		return listaReportisticaLuoghiInteresse;
	}
		
	public List<ReportisticaTickets> selectReportisticaTickets(String uid) throws DAException , SQLException{
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		ReportisticaTickets reportisticaRetrieved = null;
		List<ReportisticaTickets> listaReportisticaTickets = new ArrayList<ReportisticaTickets>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionManager.getPooledConnection();
			sql.append(" (SELECT TACE.ANNO , TACE.MESE , TACE.GIORNO , TACE.QUANTITA , TACE.QUANTITA_RESIDUA , TACE.VENDUTI , NVL(TAAC.CONVALIDATO,0) CONVALIDATO , NVL (TAAC.INCASSO,0) INCASSO FROM ");
			sql.append(" (SELECT TACE.ANNO , TACE.MESE , TACE.GIORNO , SUM (TPOE.QUANTITA) AS QUANTITA , SUM (TAOE.QUANTITA_RESIDUA) AS QUANTITA_RESIDUA , SUM ((TPOE.QUANTITA - TAOE.QUANTITA_RESIDUA)) VENDUTI ");
			sql.append(" FROM TA_CALENDARIO_EVENTI TACE , TA_ORARI_EVENTI TAOE , TP_ORARI_EVENTI TPOE ");
			sql.append(" WHERE TACE.UID_TICKETS = ? ");
			sql.append(" AND TACE.UID_CALENDARIO_EVENTI = TAOE.UID_CALENDARIO_EVENTI ");
			sql.append(" AND TAOE.UID_TP_ORARIO_EVENTI = TPOE.UID_TP_ORARI_EVENTI ");
			sql.append(" GROUP BY TACE.ANNO , TACE.MESE , TACE.GIORNO) TACE , ");
			sql.append(" (SELECT ANNO , MESE , GIORNO , SUM ((CASE WHEN CONVALIDATO = 'Y' THEN  '1' ELSE '0' END)) AS CONVALIDATO , SUM (PREZZO_TICKET) AS INCASSO ");
			sql.append(" FROM TA_ACQUISTI ");
			sql.append(" WHERE UID_TICKETS = ? GROUP BY ANNO,MESE , GIORNO) TAAC ");
			sql.append(" WHERE TACE.ANNO = TAAC.ANNO (+) AND TACE.MESE = TAAC.MESE (+) AND TACE.GIORNO = TAAC.GIORNO (+)) ");
			sql.append(" UNION ");
			sql.append(" (SELECT TACE.ANNO , TACE.MESE , TACE.GIORNO , TACE.QUANTITA , TACE.QUANTITA_RESIDUA , TACE.VENDUTI , NVL(TAAC.CONVALIDATO,0) CONVALIDATO , NVL (TAAC.INCASSO,0) INCASSO FROM ");
			sql.append(" (SELECT TACE.ANNO , TACE.MESE , NULL AS GIORNO , SUM (TPOE.QUANTITA) AS QUANTITA , SUM (TAOE.QUANTITA_RESIDUA) AS QUANTITA_RESIDUA , SUM ((TPOE.QUANTITA - TAOE.QUANTITA_RESIDUA)) VENDUTI ");
			sql.append(" FROM TA_CALENDARIO_EVENTI TACE , TA_ORARI_EVENTI TAOE , TP_ORARI_EVENTI TPOE ");
			sql.append(" WHERE TACE.UID_TICKETS = ? ");
			sql.append(" AND TACE.UID_CALENDARIO_EVENTI = TAOE.UID_CALENDARIO_EVENTI ");
			sql.append(" AND TAOE.UID_TP_ORARIO_EVENTI = TPOE.UID_TP_ORARI_EVENTI ");
			sql.append(" GROUP BY TACE.ANNO , TACE.MESE) TACE , ");
			sql.append(" (SELECT ANNO , MESE , NULL AS GIORNO , SUM ((CASE WHEN CONVALIDATO = 'Y' THEN  '1' ELSE '0' END)) AS CONVALIDATO , SUM (PREZZO_TICKET) AS INCASSO ");
			sql.append(" FROM TA_ACQUISTI ");
			sql.append(" WHERE UID_TICKETS = ? GROUP BY ANNO,MESE) TAAC ");
			sql.append(" WHERE TACE.ANNO = TAAC.ANNO (+) AND TACE.MESE = TAAC.MESE (+)) ");
			sql.append(" UNION ");
			sql.append(" (SELECT TACE.ANNO , TACE.MESE , TACE.GIORNO , TACE.QUANTITA , TACE.QUANTITA_RESIDUA , TACE.VENDUTI , NVL(TAAC.CONVALIDATO,0) CONVALIDATO , NVL (TAAC.INCASSO,0) INCASSO FROM ");
			sql.append(" (SELECT TACE.ANNO , NULL AS MESE , NULL AS GIORNO , SUM (TPOE.QUANTITA) AS QUANTITA , SUM (TAOE.QUANTITA_RESIDUA) AS QUANTITA_RESIDUA , SUM ((TPOE.QUANTITA - TAOE.QUANTITA_RESIDUA)) VENDUTI ");
			sql.append(" FROM TA_CALENDARIO_EVENTI TACE , TA_ORARI_EVENTI TAOE , TP_ORARI_EVENTI TPOE ");
			sql.append(" WHERE TACE.UID_TICKETS = ? ");
			sql.append(" AND TACE.UID_CALENDARIO_EVENTI = TAOE.UID_CALENDARIO_EVENTI ");
			sql.append(" AND TAOE.UID_TP_ORARIO_EVENTI = TPOE.UID_TP_ORARI_EVENTI ");
			sql.append(" GROUP BY TACE.ANNO) TACE , ");
			sql.append(" (SELECT ANNO , NULL AS MESE , NULL AS GIORNO , SUM ((CASE WHEN CONVALIDATO = 'Y' THEN  '1' ELSE '0' END)) AS CONVALIDATO , SUM (PREZZO_TICKET) AS INCASSO ");
			sql.append(" FROM TA_ACQUISTI ");
			sql.append(" WHERE UID_TICKETS = ? GROUP BY ANNO) TAAC ");
			sql.append(" WHERE TACE.ANNO = TAAC.ANNO (+)) ");
			sql.append(" UNION ");
			sql.append(" (SELECT TACE.ANNO , TACE.MESE , TACE.GIORNO , TACE.QUANTITA , TACE.QUANTITA_RESIDUA , TACE.VENDUTI , NVL(TAAC.CONVALIDATO,0) CONVALIDATO , NVL (TAAC.INCASSO,0) INCASSO FROM ");
			sql.append(" (SELECT NULL AS ANNO , NULL AS MESE , NULL AS GIORNO , SUM (TPOE.QUANTITA) AS QUANTITA , SUM (TAOE.QUANTITA_RESIDUA) AS QUANTITA_RESIDUA , SUM ((TPOE.QUANTITA - TAOE.QUANTITA_RESIDUA)) VENDUTI ");
			sql.append(" FROM TA_CALENDARIO_EVENTI TACE , TA_ORARI_EVENTI TAOE , TP_ORARI_EVENTI TPOE ");
			sql.append(" WHERE TACE.UID_TICKETS = ? ");
			sql.append(" AND TACE.UID_CALENDARIO_EVENTI = TAOE.UID_CALENDARIO_EVENTI ");
			sql.append(" AND TAOE.UID_TP_ORARIO_EVENTI = TPOE.UID_TP_ORARI_EVENTI) TACE , ");
			sql.append(" (SELECT NULL AS ANNO , NULL AS MESE , NULL AS GIORNO , SUM ((CASE WHEN CONVALIDATO = 'Y' THEN  '1' ELSE '0' END)) AS CONVALIDATO , SUM (PREZZO_TICKET) AS INCASSO ");
			sql.append(" FROM TA_ACQUISTI ");
			sql.append(" WHERE UID_TICKETS = ?) TAAC) ");
			sql.append(" ORDER BY ANNO DESC , MESE DESC , GIORNO DESC ");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1 , uid);
			ps.setString(2 , uid);
			ps.setString(3 , uid);
			ps.setString(4 , uid);
			ps.setString(5 , uid);
			ps.setString(6 , uid);
			ps.setString(7 , uid);
			ps.setString(8 , uid);
			rs = ps.executeQuery();
			while (rs.next()) {
				reportisticaRetrieved = new ReportisticaTickets();
				reportisticaRetrieved.setGiorno(rs.getString("GIORNO"));
				reportisticaRetrieved.setMese(rs.getString("MESE"));
				reportisticaRetrieved.setAnno(rs.getString("ANNO"));
				reportisticaRetrieved.setQuantita(rs.getString("QUANTITA"));
				reportisticaRetrieved.setQuantitaResidua(rs.getString("QUANTITA_RESIDUA"));
				reportisticaRetrieved.setVenduti(rs.getString("VENDUTI"));
				reportisticaRetrieved.setConvalidato(rs.getString("CONVALIDATO"));
				reportisticaRetrieved.setIncasso(rs.getString("INCASSO"));
				listaReportisticaTickets.add(reportisticaRetrieved);
			}
		} catch (SQLException sqlE) {
			//log.error(sqlE);
			log.error(context.getMessage("error.priceIsRequired", null, country)+ " : " + sqlE);
			throw new DAException(sqlE.getMessage());
		} finally {
			ps.close();
			rs.close();
			con.close();
		}
		return listaReportisticaTickets;
	}
}