package com.kfs.voice.script

import com.kfs.voice.vo.ReadExamAttrs
import groovy.json.JsonBuilder

/**
 * cn word
 * xmlResult.read_word.rec_paper.read_word[0].attributes()
 * content beg_pos end_pos  total_score fluency_score   tone_score  phone_score integrity_score  except_info time_len
 *
 * cn sentence
 * xmlResult.read_sentence.rec_paper.read_sentence[0].attributes()
 * content beg_pos end_pos  total_score accuracy_score  fluency_score   tone_score  phone_score integrity_score emotion_score    except_info is_rejected time_len
 *
 * en word
 * xmlResult.read_word.rec_paper.read_word[0].attributes()
 * content beg_pos end_pos  total_score accuracy_score  standard_score  except_info is_rejected reject_type
 *
 * en sentence
 * xmlResult.read_sentence.rec_paper.read_chapter[0].attributes()
 * content beg_pos end_pos  total_score accuracy_score  fluency_score   standard_score  integrity_score   except_info is_rejected reject_type score_pattern   word_count
 */


def parseXml(args) {
    def ent = args[0], category = args[1], resultXmlStr = args[2];

    ReadExamAttrs readExamAttrs = new ReadExamAttrs();

    def xmlResult = new XmlParser().parseText(resultXmlStr)

    if (category.equals("read_word")) {
        if (ent.equals("cn_vip")) {
            buildCnReadWordAttrsObj(xmlResult, readExamAttrs)
        } else if (ent.equals("en_vip")) {
            buildEnReadWordAttrsObj(xmlResult, readExamAttrs)
        }
    } else if (category.equals("read_sentence")) {
        if (ent.equals("cn_vip")) {
            buildCnSentenceAttrsObj(xmlResult, readExamAttrs)
        } else if (ent.equals("en_vip")) {
            buildEnSentenceAttrsObj(xmlResult, readExamAttrs)
        }
    }


    return new JsonBuilder(readExamAttrs).toString()
}

private void buildEnSentenceAttrsObj(Node xmlResult, ReadExamAttrs readExamAttrs) {
    def readEnSentenceAttrs = xmlResult.read_sentence.rec_paper.read_chapter[0].attributes()

    readExamAttrs.setContent(readEnSentenceAttrs.get("content"))
    readExamAttrs.setBegPos(Integer.valueOf(readEnSentenceAttrs.get("beg_pos")))
    readExamAttrs.setEndPos(Integer.valueOf(readEnSentenceAttrs.get("end_pos")))
    readExamAttrs.setTotalScore(Double.valueOf(readEnSentenceAttrs.get("total_score")))
    readExamAttrs.setAccuracyScore(Double.valueOf(readEnSentenceAttrs.get("accuracy_score")))
    readExamAttrs.setFluencyScore(Double.valueOf(readEnSentenceAttrs.get("fluency_score")))
    readExamAttrs.setStandardScore(Double.valueOf(readEnSentenceAttrs.get("standard_score")))
    readExamAttrs.setIntegrityScore(Double.valueOf(readEnSentenceAttrs.get("integrity_score")))
    readExamAttrs.setExceptInfo(readEnSentenceAttrs.get("except_info"))
    readExamAttrs.setIsRejected(Boolean.valueOf(readEnSentenceAttrs.get("is_rejected")))
    readExamAttrs.setRejectType(readEnSentenceAttrs.get("reject_type"))
    readExamAttrs.setScorePattern(readEnSentenceAttrs.get("score_pattern"))
    readExamAttrs.setWordCount(Integer.valueOf(readEnSentenceAttrs.get("word_count")))
}

private void buildCnSentenceAttrsObj(Node xmlResult, ReadExamAttrs readExamAttrs) {
    def readCnSentenceAttrs = xmlResult.read_sentence.rec_paper.read_sentence[0].attributes()

    readExamAttrs.setContent(readCnSentenceAttrs.get("content"))
    readExamAttrs.setBegPos(Integer.valueOf(readCnSentenceAttrs.get("beg_pos")))
    readExamAttrs.setEndPos(Integer.valueOf(readCnSentenceAttrs.get("end_pos")))
    readExamAttrs.setTotalScore(Double.valueOf(readCnSentenceAttrs.get("total_score")))
    readExamAttrs.setAccuracyScore(Double.valueOf(readCnSentenceAttrs.get("accuracy_score")))
    readExamAttrs.setFluencyScore(Double.valueOf(readCnSentenceAttrs.get("fluency_score")))
    readExamAttrs.setToneScore(Double.valueOf(readCnSentenceAttrs.get("tone_score")))
    readExamAttrs.setPhoneScore(Double.valueOf(readCnSentenceAttrs.get("phone_score")))
    readExamAttrs.setIntegrityScore(Double.valueOf(readCnSentenceAttrs.get("integrity_score")))
    readExamAttrs.setEmotionScore(Double.valueOf(readCnSentenceAttrs.get("emotion_score")))
    readExamAttrs.setExceptInfo(readCnSentenceAttrs.get("except_info"))
    readExamAttrs.setIsRejected(Boolean.valueOf(readCnSentenceAttrs.get("is_rejected")))
    readExamAttrs.setTimeLen(Integer.valueOf(readCnSentenceAttrs.get("time_len")))
}

private void buildEnReadWordAttrsObj(Node xmlResult, ReadExamAttrs readExamAttrs) {
    def readEnWordAttrs = xmlResult.read_word.rec_paper.read_word[0].attributes()

    readExamAttrs.setContent(readEnWordAttrs.get("content"))
    readExamAttrs.setBegPos(Integer.valueOf(readEnWordAttrs.get("beg_pos")))
    readExamAttrs.setEndPos(Integer.valueOf(readEnWordAttrs.get("end_pos")))
    readExamAttrs.setTotalScore(Double.valueOf(readEnWordAttrs.get("total_score")))
    readExamAttrs.setAccuracyScore(Double.valueOf(readEnWordAttrs.get("accuracy_score")))
    readExamAttrs.setStandardScore(Double.valueOf(readEnWordAttrs.get("standard_score")))
    readExamAttrs.setExceptInfo(readEnWordAttrs.get("except_info"))
    readExamAttrs.setIsRejected(Boolean.valueOf(readEnWordAttrs.get("is_rejected")))
    readExamAttrs.setRejectType(readEnWordAttrs.get("reject_type"))
}

private void buildCnReadWordAttrsObj(Node xmlResult, ReadExamAttrs readExamAttrs) {
    def readCnWordAttrs = xmlResult.read_word.rec_paper.read_word[0].attributes()

    readExamAttrs.setContent(readCnWordAttrs.get("content"))
    readExamAttrs.setBegPos(Integer.valueOf(readCnWordAttrs.get("beg_pos")))
    readExamAttrs.setEndPos(Integer.valueOf(readCnWordAttrs.get("end_pos")))
    readExamAttrs.setTotalScore(Double.valueOf(readCnWordAttrs.get("total_score")))
    readExamAttrs.setFluencyScore(Double.valueOf(readCnWordAttrs.get("fluency_score")))
    readExamAttrs.setToneScore(Double.valueOf(readCnWordAttrs.get("tone_score")))
    readExamAttrs.setPhoneScore(Double.valueOf(readCnWordAttrs.get("phone_score")))
    readExamAttrs.setIntegrityScore(Double.valueOf(readCnWordAttrs.get("integrity_score")))
    readExamAttrs.setExceptInfo(readCnWordAttrs.get("except_info"))
    readExamAttrs.setTimeLen(Integer.valueOf(readCnWordAttrs.get("time_len")))
}

parseXml(args);