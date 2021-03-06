------------------------------------------------------------------------------
Dokumentname:   ${document.name?html}
------------------------------------------------------------------------------

   <#if document.properties.title?exists>
Titel:   ${document.properties.title?html}
   <#else>
Titel:          KEINER
   </#if>
   <#if document.properties.description?exists>
Beschreibung:   ${document.properties.description?html}
   <#else>
Beschreibung:   KEINE
   </#if>
Ersteller:   ${document.properties.creator?html}
Erstellt:    ${document.properties.created?datetime}
Bearbeiter:  ${document.properties.modifier?html}
Geändert:    ${document.properties.modified?datetime}
Größe:       ${document.size / 1024} KB


Links zum Inhalt

Inhaltsordner:     ${contentFolderUrl?html}
URL zum Inhalt:    ${shareContextUrl}/proxy/alfresco/api/node/content/${document.storeType}/${document.storeId}/${document.id}/${document.name?html}
URL zum Download:  ${shareContextUrl}/proxy/alfresco/api/node/content/${document.storeType}/${document.storeId}/${document.id}/${document.name?html}?a=true
