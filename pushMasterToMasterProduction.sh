git remote add cd git@github.com:joaomneto/TitanCompanion_CD.git 2> /dev/null
git fetch --all
git checkout master
git pull
git push -f cd master:master_production
