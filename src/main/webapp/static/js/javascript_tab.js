/**
 * Created by oksite on 2016/7/15.
 * www.oksite.com.cn
 */
window.onload=function(){

    tabsub('n_tab_ul','all_body');

    }

function tabsub(ida,idb){    

    var lis=document.getElementById(ida).getElementsByTagName('li');    

    var divs=document.getElementById(idb).getElementsByClassName('all');

    if(lis.length!=divs.length){      

        return;    

        };

    for(var i=0;i<lis.length;i++){
        
        lis[i].id=i;
       
        lis[i].onclick=function(){

            for(var j=0;j<lis.length;j++){

                lis[j].className='';

                divs[j].style.display='none';

                }

            this.className='select';

            divs[this.id].style.display='block';
            
            
            }
        }
        
    }