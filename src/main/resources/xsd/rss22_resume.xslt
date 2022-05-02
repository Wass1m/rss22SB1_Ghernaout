<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://univrouen.fr/rss22"
>

    <xsl:output method="html"
                doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
                omit-xml-declaration="yes" encoding="UTF-8" indent="yes" />

    <xsl:template match="/">
        <xsl:element name="html">
            <xsl:element name="head">
                <xsl:element name="title">
                    document
                </xsl:element>
                <xsl:element name="link">
                    <xsl:attribute name="href">
                        <xsl:text>style.css</xsl:text>
                    </xsl:attribute>
                    <xsl:attribute name="rel">
                        <xsl:text>stylesheet</xsl:text>
                    </xsl:attribute>
                </xsl:element>
            </xsl:element>
            <xsl:element name="body">
                <xsl:element name="h1">
                    PROJET ANNUEL - Flux RSS22
                </xsl:element>
                <xsl:element name="p">
                    <xsl:variable name="date" select="fn:current-date()"/>
                    Le <xsl:value-of select='fn:format-date($date,"[M01]/[D01]/[Y0001]")'/>
                </xsl:element>

                <xsl:apply-templates select = "ns2:feed"/>
            </xsl:element>
        </xsl:element>

    </xsl:template>


    <xsl:template match="ns2:feed">
        <h2> Description</h2>
        <xsl:element name="ul">
            <xsl:element name="li">
                <xsl:text>Contenu : </xsl:text>
                <xsl:value-of select="title"/>
            </xsl:element>
            <xsl:element name="li"><xsl:text>publié le  </xsl:text>
                <xsl:call-template name="dateformat">
                    <xsl:with-param name="date" select="pubDate" />
                </xsl:call-template>
            </xsl:element>
            <xsl:element name="li">
                <xsl:text>copyright </xsl:text>
                <xsl:value-of select="copyright"/>
            </xsl:element>
        </xsl:element>
        <h2> Sommaire </h2>

        <table>
            <thead>
                <th>GUID</th>
                <th>Titre</th>
                <th>Date</th>
            </thead>
            <tbody>
                <xsl:variable name="total" select="count(item)" />
                <xsl:for-each select="item">
                    <xsl:sort select="published" order="descending" />
                    <xsl:variable name="count" select="position()"/>
                    <xsl:variable name="categ" select="category/@term"/>
                    <xsl:variable name="titleName" select="title" />
                    <xsl:element name="tr">
                        <xsl:attribute name="class"><xsl:value-of select="category/@term"/></xsl:attribute>
<!--                        <td><xsl:value-of select="$count"/>/<xsl:value-of select="$total"/></td>-->
                        <td><xsl:value-of select="guid"/></td>
                        <td><xsl:value-of select="substring($titleName,0,44)"/>...</td>
                        <td><xsl:value-of select="published"/></td>
                    </xsl:element>
                </xsl:for-each>
            </tbody>
        </table>

        <!--		  	 <h2> Details des informations </h2>-->
        <!--		  	 -->
        <!--		  	 -->
        <!--		  	 	<xsl:apply-templates select = "item"/>-->

    </xsl:template>



    <!--<xsl:template match = "synthese">-->

    <!--		<table>-->
    <!--		<thead>-->
    <!--		<th>title</th>-->
    <!--			<th>date</th>-->
    <!--				<th>cat</th>-->
    <!--					<th>author</th>-->
    <!--		</thead>	-->
    <!--		  		<tbody>-->
    <!--		  			<xsl:for-each select="item">-->
    <!--		  				<tr>-->
    <!--		  					<td><xsl:value-of select="title"/></td>-->
    <!--		  					<td><xsl:value-of select="date"/></td>-->
    <!--		  					<td>(<xsl:value-of select="category"/>)</td>-->
    <!--		  					<td><xsl:value-of select="author"/></td>-->
    <!--		  				</tr>-->
    <!--		  			</xsl:for-each>-->
    <!--		  		</tbody>-->
    <!--		  	</table>-->
    <!--</xsl:template>-->

    <!--<xsl:template match = "item">-->
    <!--	<xsl:element name="h3">-->
    <!--            <xsl:value-of select="title"/>-->
    <!--          </xsl:element>-->
    <!--          <xsl:element name="p" >-->
    <!--             <xsl:attribute name="style">-->
    <!--                  font-style : italic-->
    <!--             </xsl:attribute>-->
    <!--          	(<xsl:value-of select="guid"/>)-->
    <!--          </xsl:element>-->
    <!--          <xsl:element  name="image">-->
    <!--          	<xsl:attribute name="src">-->
    <!--			<xsl:value-of select="image/@href"/>-->
    <!--			</xsl:attribute>-->
    <!--          	<xsl:attribute name="alt">-->
    <!--			<xsl:value-of select="image/@alt"/>-->
    <!--			</xsl:attribute>-->
    <!--        	 <xsl:attribute name="style">-->
    <!--         	width:500px;height:300px-->
    <!--			-->
    <!--			</xsl:attribute>-->
    <!--          </xsl:element>-->
    <!--          	<xsl:element name="p">-->
    <!--          	Category :-->
    <!--            <xsl:value-of select="category/@term"/>-->
    <!--            -->
    <!--          </xsl:element>-->
    <!--           	<xsl:element name="p">-->
    <!--          	Publie le :-->
    <!--    -->
    <!--            -->
    <!--            <xsl:call-template name="dateformat">-->
    <!--				<xsl:with-param name="date" select="published" />-->
    <!--			</xsl:call-template>-->
    <!--            -->
    <!--          </xsl:element>-->
    <!--</xsl:template>-->



    <xsl:template name="dateformat">
        <xsl:param name="date" />
        <xsl:variable name="dd" select="substring($date,0,2)" />
        <xsl:variable name="mm" select="substring($date,6,2)" />
        <xsl:variable name="yyyy" select="substring($date,1,4)" />
        <xsl:variable name="hour" select="substring($date,12,2)" />
        <xsl:variable name="min" select="substring($date,15,2)" />

        <xsl:value-of select="$dd" />
        <xsl:choose>
            <xsl:when test="$mm = '00'">
                Janv
            </xsl:when>
            <xsl:when test="$mm = '01'">
                Févr
            </xsl:when>
            <xsl:when test="$mm = '02'">
                Mars
            </xsl:when>
            <xsl:when test="$mm = '03'">
                Avri
            </xsl:when>
            <xsl:when test="$mm = '04'">
                Mai
            </xsl:when>
            <xsl:when test="$mm = '05'">
                Juin
            </xsl:when>
            <xsl:when test="$mm = '06'">
                Juil
            </xsl:when>
            <xsl:when test="$mm = '07'">
                Août
            </xsl:when>
            <xsl:when test="$mm = '08'">
                Sept
            </xsl:when>
            <xsl:when test="$mm = '09'">
                Octo
            </xsl:when>
            <xsl:when test="$mm = '10'">
                Nove
            </xsl:when>
            <xsl:when test="$mm = '11'">
                Déce
            </xsl:when>
        </xsl:choose>
        <xsl:text> </xsl:text>
        <xsl:value-of select="$yyyy" />
        &#160;à&#160;
        <xsl:value-of select="$hour" />:
        <xsl:value-of select="$min" />
    </xsl:template>


</xsl:stylesheet>