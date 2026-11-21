// Vstupní šablona pro Azure Developer CLI (azd).
// Vytvoří resource group a v ní App Service plán + webovou aplikaci (viz resources.bicep).
targetScope = 'subscription'

@minLength(1)
@maxLength(64)
@description('Název prostředí z azd, např. javadays-demo')
param environmentName string

@minLength(1)
@description('Azure region, např. westeurope nebo swedencentral')
param location string

var tags = {
  'azd-env-name': environmentName
}

// Krátký unikátní řetězec, aby byly názvy prostředků globálně jedinečné.
var resourceToken = toLower(uniqueString(subscription().id, environmentName, location))

resource rg 'Microsoft.Resources/resourceGroups@2024-03-01' = {
  name: 'rg-${environmentName}'
  location: location
  tags: tags
}

module resources 'resources.bicep' = {
  name: 'resources'
  scope: rg
  params: {
    location: location
    tags: tags
    resourceToken: resourceToken
  }
}

output AZURE_LOCATION string = location
output WEB_URI string = resources.outputs.webUri
