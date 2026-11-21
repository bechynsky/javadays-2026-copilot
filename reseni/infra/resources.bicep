// App Service plán (Linux, B1) + webová aplikace s Java 25 (Java SE – spustitelný JAR).
param location string
param tags object
param resourceToken string

resource plan 'Microsoft.Web/serverfarms@2024-04-01' = {
  name: 'plan-${resourceToken}'
  location: location
  tags: tags
  kind: 'linux'
  sku: {
    name: 'B1'
  }
  properties: {
    reserved: true // povinné pro Linux
  }
}

resource web 'Microsoft.Web/sites@2024-04-01' = {
  name: 'app-${resourceToken}'
  location: location
  // Podle tohoto tagu azd pozná, kam nasadit službu "api" z azure.yaml.
  tags: union(tags, { 'azd-service-name': 'api' })
  kind: 'app,linux'
  properties: {
    serverFarmId: plan.id
    httpsOnly: true
    siteConfig: {
      linuxFxVersion: 'JAVA|25-java25'
      alwaysOn: true
      ftpsState: 'Disabled'
      minTlsVersion: '1.2'
      appSettings: [
        {
          // App Service posílá provoz na port 80 – Spring Boot ho převezme z proměnné SERVER_PORT.
          name: 'SERVER_PORT'
          value: '80'
        }
      ]
    }
  }
}

output webUri string = 'https://${web.properties.defaultHostName}'
