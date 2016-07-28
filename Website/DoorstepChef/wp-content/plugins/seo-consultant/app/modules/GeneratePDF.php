<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;

if (!defined('FPDF_VERSION')) {
    require 'FPDF.php';
}

class GeneratePDF extends \FPDF
{
    public $supportedTypes = array(
        'statistics', 'pages', 'domains', 'anchors'
    );

    private $table_widths = array(
        'statistics' => array(40, 35, 40, 45),
        'pages' => array(10, 70, 35, 120, 30),
        'domains' => array(10, 80, 50, 30),
        'anchors' => array(40, 40),
    );


    function setHeading($type)
    {
        // Arial bold 15
        $this->SetFont('Arial','B',18);
        // Title
        $this->Text(10,10,strtoupper($type));
        // Line break
        $this->Ln(20);
    }

    public function generateContent($type)
    {

        if (!in_array($type, $this->supportedTypes)) {
            $this->redirect();
        }


        $pdf = new GeneratePDF();
        $pdf->SetFont('Arial', '', 11);
        $pdf->AddPage('P', 'A3');
        $pdf->setHeading($type);
        $pdf->{ucfirst($type)}();
        $pdf->Output($type . '-seo-consultant-report.pdf', 'D');
    }

    private function Statistics()
    {
        $db = Database::getInstance();
        $_follow = $db->getStatisticsOfFollow();
        $_backlinks = $db->getStaticsticsOfBacklinksQnt();
        $_refdomains = $db->getStaticsticsOfRefDomainsQnt();

        $contents = array(
            'Nofollow/Dofollow' => $this->percent($_follow[0]['NOFOLLOW']) . '/' . $this->percent($_follow[0]['FOLLOW']),
            'Backlinks' => $_backlinks[0]['LINKS'],
            'Backlinks Lost' => 0,
            'Referring Domains' => $_refdomains[0]['DOMAINS'],
            'Referring Domains Lost' => 0
        );


       $this->printPresentationContent($contents);
    }

    private function Domains()
    {
        $db = Database::getInstance();
        $_domains = $db->getDomains();

        $this->formatpdf($_domains, $this->table_widths['domains']);
    }

    private function Pages()
    {
        $db = Database::getInstance();
        $_pages = $db->getLog();

        $this->formatpdf($_pages, $this->table_widths['pages']);
    }

    private function Anchors()
    {
        $db = Database::getInstance();
        $_anchors = $db->getStaticsticsOfAnchorTextDistribution();

        $this->formatpdf($_anchors, $this->table_widths['anchors']);
    }

    private function formatpdf($queried, array $table_widths)
    {
        $this->SetFont('Arial', '', 11);
        // Data
        foreach ($queried as $index => $row) {
            if ($index === 0) {
                $headings = array_keys($row);
                $hcounter = 0;
                foreach ($headings as $heading) {
                    $this->Cell($table_widths[$hcounter], 7, $heading, 1);
                    $hcounter++;
                }
                $this->Ln();
            }
            $vcounter = 0;
            foreach ($row as $index => $value) {
                if(strlen($value) > 60){
                    $value = substr($value, 0,57)."...";
                }
                $this->Cell($table_widths[$vcounter], 8, $value, 1);
                $vcounter++;
            }
            $this->Ln();
        }

    }

    private function printPresentationContent($content){
        if(!is_array($content)) return null;

        $counter = 0;
        foreach($content as $header => $text){
            $line = $counter * 15;
            $this->SetFont('Arial', '', 16);
            $this->Text(10,30 + $line ,$header);
            $this->SetFont('Arial', '', 11);
            $this->Text(30,40 + $line - 5 ,$text);
            $this->Ln(20);
            $counter++;
        }
    }

    private function redirect()
    {
        header('Location:' . admin_url());
        exit();
    }

    private function percent($value)
    {
        return (number_format($value, 2) * 100) . '%';
    }

}