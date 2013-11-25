/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

function changeBack(form){
        //alert("You have selected " + form.name);

         var mySelect;
        var allSelect = form.getElementsByTagName('select');
        // alert("You have selected " + allSelect[j].options.selectedIndex);
        for  (var j=0; j< allSelect.length;j++){
            if(allSelect[j].name == "idTypeGenus") {
               if (allSelect[j].options.selectedIndex ==0){
                  toggleDivNew('cinema');
                  toggleDivNew('concerto');
                  toggleDivNew('opera');
                  toggleDivNew('cinema1');
                  toggleDivNew('concerto1');
                  toggleDivNew('opera1');
                  break;
               }
                if(allSelect[j].options.selectedIndex ==3){
                      toggleDivNew('cinema');
                      toggleDivNew('concerto');
                      toggleDivNew('opera');
                      toggleDivNew('cinema1');
                      toggleDivNew('concerto1');
                      toggleDivNew('opera1');
                      break;
                }
                if (allSelect[j].options.selectedIndex == 1){
                          toggleDiv('cinema');
                          toggleDiv('cinema1');
                          toggleDivNew('concerto');
                          toggleDivNew('opera');
                          toggleDivNew('concerto1');
                          toggleDivNew('opera1');
                          break;
               }
                if (allSelect[j].options.selectedIndex == 2){
                          toggleDiv('concerto');
                          toggleDiv('concerto1');
                          toggleDivNew('cinema');
                          toggleDivNew('cinema1');
                          toggleDivNew('opera');
                          toggleDivNew('opera1');
                          break;
               }
                if (allSelect[j].options.selectedIndex == 4){
                          toggleDiv('opera');
                          toggleDiv('opera1');
                          toggleDivNew('cinema');
                          toggleDivNew('concerto');
                          toggleDivNew('cinema1');
                          toggleDivNew('concerto1');
                          break;
               }
            }
        }
    }
  function toggleDiv(divid){
     if(document.getElementById(divid).style.display == 'none'){
      document.getElementById(divid).style.display = 'block';
    }
  }

  function toggleDivNew(divid){
     if(document.getElementById(divid).style.display == 'block'){
      document.getElementById(divid).style.display = 'none';
    }
  }

