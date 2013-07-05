package com.baysearch.config

//noinspection GroovyUnusedAssignment
def site = '''

site.hostname = 'localhost'
site.exclude_homepage_from_index = 'no'
site.JAVA_FRIENDLY_SITE = 'yes'
site.DOC_TYPES_TO_INDEX = 'htm,html,jsp,xls,xlsx'

//Environments section
environments {
  dev {
    site.hostname = 'local'
    site.exclude_homepage_from_index = 'yes'
  }
  test {
    site.hostname = 'test'
    site.exclude_homepage_from_index = 'no'
  }
  prod {
    site.hostname = 'prod'
    site.exclude_homepage_from_index = 'yes'
  }
}
'''




