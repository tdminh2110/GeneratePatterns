import org.semanticweb.owlapi.model.IRI;

public class Atom 
{	
	private IRI iri;

    public Atom(IRI iri)
    {
        this.iri = iri;
    }

    public IRI getIRI()
    {
        return this.iri;
    }
}
