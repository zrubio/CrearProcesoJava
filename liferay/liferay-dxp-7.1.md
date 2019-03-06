# Liferay DXP 7.1

### Useful portal properties

{% code-tabs %}
{% code-tabs-item title="portal-ext.properties" %}
```text
#Desactivate Liferay setup Wizard
setup.wizard.enabled=false

# Https 
web.server.protocol=https

# Disable validate LPKG
# Only for development environment
module.framework.properties.lpkg.index.validator.enabled=false
```
{% endcode-tabs-item %}
{% endcode-tabs %}

