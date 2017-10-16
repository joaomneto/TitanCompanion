git remote add cd git@github.com:joaomneto/TitanCompanion_CD.git 2> /dev/null
git push -f cd $(git rev-parse --abbrev-ref HEAD):master_production
