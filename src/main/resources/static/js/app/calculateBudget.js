function calculateBudget() {
    var budget = 0;
    var costs = $('input[name="costs"]');

    for(var i=0; i<costs.length; i++) {
        var cost = costs.eq(i).val()
        if(!(cost=="" || cost==null || cost==undefined || (cost!=null && typeof cost=="object" && !Object.keys(cost).length))){
            budget += parseInt(cost);
        }
    }

    $('#budget').text(budget);
}

export {calculateBudget};