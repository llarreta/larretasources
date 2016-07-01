select idContainer, idElement 
from containedelement
order by idContainer, idElement
group by idContainer, idElement
having count(*) > 1
count(*)